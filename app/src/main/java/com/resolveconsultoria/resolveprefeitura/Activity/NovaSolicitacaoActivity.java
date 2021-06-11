package com.resolveconsultoria.resolveprefeitura.Activity;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.resolveconsultoria.resolveprefeitura.API.Resolve;
import com.resolveconsultoria.resolveprefeitura.API.RetrofitConfig;
import com.resolveconsultoria.resolveprefeitura.Database.SharedPreferences;
import com.resolveconsultoria.resolveprefeitura.Model.Configuracoes;
import com.resolveconsultoria.resolveprefeitura.Model.Servico;
import com.resolveconsultoria.resolveprefeitura.Model.Solicitacao;
import com.resolveconsultoria.resolveprefeitura.Model.Usuario;
import com.resolveconsultoria.resolveprefeitura.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.resolveconsultoria.resolveprefeitura.Utils.CameraUtils;
import com.resolveconsultoria.resolveprefeitura.Utils.GPSTracker;
import com.resolveconsultoria.resolveprefeitura.Utils.ProgressRequestBody;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NovaSolicitacaoActivity extends AppCompatActivity implements ProgressRequestBody.UploadCallbacks {

    private String[] permissoes = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE
    };

    private Toolbar toolbar;
    private Servico servico = new Servico();

    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;

    // key to store image path in savedInstance state
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final int LOCATION =3;

    // Bitmap sampling size
    public static final int BITMAP_SAMPLE_SIZE = 8;

    // Gallery directory name to store the images or videos
    public static final String GALLERY_DIRECTORY_NAME = "Hello Camera";

    // Image and Video file extensions
    public static final String IMAGE_EXTENSION = "jpg";
    public static final String VIDEO_EXTENSION = "mp4";

    private static String imageStoragePath;

    private AlertDialog.Builder alertDialog;
    private ImageView imgPreview;
    private VideoView videoPreview;
    private Switch posicao;
    private TextInputEditText descricao;
    private Configuracoes configuracoes;
    private GPSTracker gps;
    private Solicitacao solicitacao;
    private Usuario usuario ;
    private int totalCaracteres = 240;
    private TextView totalCaracteresDigitados;

    private Retrofit retrofit;
    private Resolve resolve;
    private SharedPreferences sharedPreferences;

    private ProgressBar progressBar;
    private ProgressRequestBody fileBody;

    private MaterialEditText endereco;
    private MaterialEditText numero;
    private MaterialEditText bairro;
    private MaterialEditText cep;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_solicitacao);

        servico = (Servico) getIntent().getSerializableExtra( "Servico" );

        inicializaComponentes ();

        usuario = sharedPreferences.recupraDadosPessoais();

        toolbar.setTitle(servico.getDescricao());

        configuracoes = sharedPreferences.recupraConfiguracoes();

        if (configuracoes != null){
            posicao.setChecked(configuracoes.isGPS());
        }

        gps = new GPSTracker( getApplicationContext() );

        imgPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opcaoCapture();
            }
        });
        videoPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opcaoCapture();
            }
        });
        posicao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged (CompoundButton compoundButton, boolean b) {

                if (configuracoes == null) {
                    configuracoes = new Configuracoes();
                }

                configuracoes.setGPS(b);
                sharedPreferences.atualizaConfiguracoes(configuracoes);

                if (b){
                    if(!compoundButton.isPressed()) {
                        Dexter.withActivity(NovaSolicitacaoActivity.this)
                                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                                .withListener(new MultiplePermissionsListener() {
                                    @Override
                                    public void onPermissionsChecked (MultiplePermissionsReport report) {
                                        if (report.areAllPermissionsGranted()) {


                                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                                            showPermissionsAlert();
                                        }
                                    }

                                    @Override
                                    public void onPermissionRationaleShouldBeShown (List<PermissionRequest> permissions, PermissionToken token) {
                                        token.continuePermissionRequest();
                                    }
                                }).check();
                    }

                    if (!gps.canGetLocation()){
                        showAlertGPS();
                    }
                }
            }
        });

        restoreFromBundle(savedInstanceState);

        totalCaracteresDigitados.setText( getString( R.string.novasolicitacao_caracteres_restantes, 240 ) );

        descricao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {

                totalCaracteresDigitados.setText( getString( R.string.novasolicitacao_caracteres_restantes, ( 240 - count ) ) );

            }

            @Override
            public void afterTextChanged (Editable s) {

            }
        });

    }

    @Override
    protected void onResume () {
        super.onResume();
        gps = new GPSTracker( getApplicationContext() );

    }

    @Override
    protected void onRestart () {
        super.onRestart();
        gps = new GPSTracker( getApplicationContext() );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Refreshing the gallery
                CameraUtils.refreshGallery(getApplicationContext(), imageStoragePath);

                // successfully captured the image
                // display it in image view
                previewCapturedImage();
            }
            else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Refreshing the gallery
                CameraUtils.refreshGallery(getApplicationContext(), imageStoragePath);

                // video successfully recorded
                // preview the recorded video
                previewVideo();
            }
            else if (resultCode == RESULT_CANCELED) {
                // user cancelled recording
                Toast.makeText(getApplicationContext(),
                        "User cancelled video recording", Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                // failed to record video
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putString(KEY_IMAGE_STORAGE_PATH, imageStoragePath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        imageStoragePath = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);
    }

    public void inicializaComponentes (){

        toolbar                  = findViewById( R.id.toolbar );
        imgPreview               = findViewById( R.id.imgPreview );
        videoPreview             = findViewById( R.id.videoPreview );
        posicao                  = findViewById( R.id.sw_Nova_Solicitacao_LocalizacaoID );
        descricao                = findViewById( R.id.ti_Descricao );
        totalCaracteresDigitados = findViewById( R.id.tv_total_caracteres );
        endereco                 = findViewById( R.id.edt_Nova_Solicitacao_Endereco );
        numero                   = findViewById( R.id.edt_Nova_Solicitacao_Numero );
        bairro                   = findViewById( R.id.edt_Nova_Solicitacao_Bairro );
        cep                      = findViewById( R.id.edt_Nova_Solicitacao_CEP );

        retrofit                 = RetrofitConfig.getRetrofit( );
        resolve                  = retrofit.create( Resolve.class);
        sharedPreferences        = new SharedPreferences( getApplicationContext() );

    }

    public void restoreFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_IMAGE_STORAGE_PATH)) {
                imageStoragePath = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);
                if (! TextUtils.isEmpty(imageStoragePath)) {
                    if (imageStoragePath.substring(imageStoragePath.lastIndexOf(".")).equals("." + IMAGE_EXTENSION)) {
                        previewCapturedImage();
                    }
                    else if (imageStoragePath.substring(imageStoragePath.lastIndexOf(".")).equals("." + VIDEO_EXTENSION)) {
                        previewVideo();
                    }
                }
            }
        }
    }

    public void requestCameraPermission(final int type) {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            if (type == MEDIA_TYPE_IMAGE) {
                                // capture picture
                                captureImage();
                            } else {
                                captureVideo();
                            }

                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            showPermissionsAlert();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    public void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CameraUtils.openSettings(NovaSolicitacaoActivity.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    public void opcaoCapture (){

        String[] opcoes = {"Foto", "Video", "Galeria"};

        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setIcon(R.drawable.ic_baseline_camera_alt_24);
        alertDialog.setTitle( "Foto/Video" );
        //alertDialog.setMessage( "Exportação realizada com Sucesso!" );

        alertDialog.setItems(opcoes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        if (CameraUtils.checkPermissions(getApplicationContext())) {
                            captureImage();
                        } else {
                            requestCameraPermission(MEDIA_TYPE_IMAGE);
                        }
                        break;
                    case 1:
                        if (CameraUtils.checkPermissions(getApplicationContext())) {
                            captureVideo();
                        } else {
                            requestCameraPermission(MEDIA_TYPE_VIDEO);
                        }
                        break;
                    case 2:
                        break;
                }
            }
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();

    }

    public void captureImage() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
        if (file != null) {
            imageStoragePath = file.getAbsolutePath();
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    public void captureVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_VIDEO);
        if (file != null) {
            imageStoragePath = file.getAbsolutePath();
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);

        // set video quality
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file

        // start the video capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
    }

    public void previewCapturedImage() {
        try {
            // hide video preview
            // videoPreview.setVisibility(View.GONE);

            //imgPreview.setVisibility(View.VISIBLE);

            Bitmap bitmap = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePath);

            imgPreview.setImageBitmap(bitmap);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void previewVideo() {
        try {
            // hide image preview
            imgPreview.setVisibility(View.GONE);

            videoPreview.setVisibility(View.VISIBLE);
            videoPreview.setVideoPath(imageStoragePath);
            // start playing
            videoPreview.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Uri getImageUri(Bitmap src,String nomeArquivo) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 100, os);

        String path = MediaStore.Images.Media.insertImage(getContentResolver(), src, "title", nomeArquivo);
        return Uri.parse(path);
    }

    public File recuperaFoto(){
        imgPreview.setDrawingCacheEnabled(true);
        Bitmap bitmap = imgPreview.getDrawingCache();
        return  new File( getImageUri(bitmap,"teste").getPath() );
    }

    public void showAlertGPS(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Configuração GPS");
        alertDialog.setMessage("GPS não está habilitado. Você quer ir para o menu de configurações?");
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("Configurações", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });

        alertDialog.show();

    }

    public void enviarSolicitacao(View view){

        if (verificarUsuarioLogado()) {

            solicitacao = new Solicitacao();
            SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            solicitacao.setClienteIDFK( Integer.parseInt( getResources().getString( R.string.id_Cliente ) ) );
            solicitacao.setCategoriaIDFK(servico.getCategoriaIDFK());
            solicitacao.setServicoIDFK(servico.getServicoID());
            solicitacao.setUsuarioIDFK(1);

            solicitacao.setDescricao(descricao.getText().toString());
            solicitacao.setDataSolicitacao(simpleFormatter.format(new Date()));
            solicitacao.setStatus(1);

            File file;

            if (( imgPreview.getDrawable()) != null ){
                file = recuperaFoto();
            }
            else{
                file = null;
            }

            ProgressRequestBody fileBody = new ProgressRequestBody(file, "media",this);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), fileBody);

            Log.i("Controle",new Gson().toJson(solicitacao));

            /*resolve.postSolicitacao(solicitacao,filePart).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse (Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.isSuccessful()) {
                        Toast.makeText(NovaSolicitacaoActivity.this, "Solicitação enviada", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(NovaSolicitacaoActivity.this, "Não foi possivel enviar solicitacão. " + "\n" + response.toString(), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure (Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(NovaSolicitacaoActivity.this, "Não foi possivel enviar solicitacão. " + "\n" + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });*/
        }
        else{
            alertUsuarioLogado();
        }

    }

    public void alertSucess(Solicitacao solicitacao){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Nova Solicitacao");
        alertDialog.setMessage("Sucesso ao efetuar solicitação." + "\n"+ "Número da solicitacão" + String.valueOf( solicitacao.getNumeroSolicitacao() ));
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int which) {
                finish();
            }
        });

        alertDialog.show();

    }

    public boolean verificarUsuarioLogado(){

        return  true;

//        if (usuario == null ){
//            return false;
//        }else{
//            return true;
//        }
    }

    public void alertUsuarioLogado(){
        Toast.makeText(this, "Usuario não Logado!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressUpdate (int percentage) {
        progressBar.setProgress(percentage);
    }

    @Override
    public void onError () {

    }

    @Override
    public void onFinish () {
        progressBar.setProgress(100);
    }

}