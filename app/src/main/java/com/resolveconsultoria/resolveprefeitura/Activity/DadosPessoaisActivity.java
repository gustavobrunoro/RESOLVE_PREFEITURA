package com.resolveconsultoria.resolveprefeitura.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.resolveconsultoria.resolveprefeitura.API.Resolve;
import com.resolveconsultoria.resolveprefeitura.API.RetrofitConfig;
import com.resolveconsultoria.resolveprefeitura.Database.SharedPreferences;
import com.resolveconsultoria.resolveprefeitura.Model.Usuario;
import com.resolveconsultoria.resolveprefeitura.R;
import com.santalu.maskedittext.MaskEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class DadosPessoaisActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView fotoPerfil;
    private EditText nome;
    private EditText email;
    private EditText senha;
    private MaskEditText dataNascimento;
    private MaskEditText telefone;
    private MaskEditText cep;
    private EditText cidade;
    private EditText bairro;
    private EditText endereco;
    private EditText numero;
    private EditText complemento;
    private Usuario usuario;

    private static final int selecaoCamera = 10;
    private static final int selecaoGaleraia = 20;
    private Bitmap foto;

    private Retrofit retrofit;
    private Resolve resolve;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_pessoais);

        inicializaComponentes ();
        loadDadosPessoais();
        requestCameraPermission();

        toolbar.setTitle( R.string.DadosPessoais_title );
        setSupportActionBar( toolbar );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK ){

            Bitmap imagem = null;

            try{

                switch (requestCode){

                    //Verificar se o Dado esta vindo da Camera
                    case selecaoCamera:
                        imagem = (Bitmap) data.getExtras().get("data");
                        break;

                    //Verificar se o Dado esta vindo da Galeria
                    case selecaoGaleraia:
                        Uri localImagem = data.getData();
                        imagem = MediaStore.Images.Media.getBitmap(getContentResolver(),localImagem);
                        break;
                }

                // Confirma se existe Imagem
                if (imagem != null){
                    // Seta a imagem na tela
                    fotoPerfil.setImageBitmap(imagem);
                    saveFoto(imagem);
                }

            }catch (Exception e ){
                e.printStackTrace();
            }
        }
    }

    private void inicializaComponentes () {

        toolbar        = findViewById( R.id.toolbar );
        //foto           = findViewById( R.id.profile_image );
        nome           = findViewById( R.id.edt_NomeCompleto );
        email          = findViewById( R.id.edt_Email );
        senha          = findViewById( R.id.edt_Senha );
        dataNascimento = findViewById( R.id.edt_DataNascimento );
        telefone       = findViewById( R.id.edt_Telefone );
        cep            = findViewById( R.id.edt_CEP );
        cidade         = findViewById( R.id.edt_Cidade );
        bairro         = findViewById( R.id.edt_Bairro );
        endereco       = findViewById( R.id.edt_Endereco );
        numero         = findViewById( R.id.edt_Numero );

        retrofit  = RetrofitConfig.getRetrofit(  );
        resolve   = retrofit.create( Resolve.class );

        sharedPreferences = new SharedPreferences( getApplicationContext() );
    }

    private void requestCameraPermission() {

        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

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

    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Requeridas!")
                .setMessage("A Câmera precisa de poucas permissões para funcionar corretamente. Conceda-os nas configurações")
                .setPositiveButton("Abrir Configurações", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    /**Metodo Responsavel por Abrir a camera para Tirar Foto
     */
    public void AbrirCamera(View view){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if( intent.resolveActivity(getPackageManager()) != null ) {
            startActivityForResult(intent, selecaoCamera);
        }

    }

    /**Metodo Responsavel por Abrir a Galeria das Fotos
     */
    public void AbrirGaleria(View view){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if( intent.resolveActivity(getPackageManager()) != null ) {
            startActivityForResult(intent, selecaoGaleraia);
        }

    }

    public void saveFoto(Bitmap imagem){

        File dir = new File(String.valueOf(Environment.getExternalStorageDirectory().getAbsoluteFile()));

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (!dir.exists())
                dir.mkdirs();
        }

        try {

            byte[] bytes;
            FileOutputStream fos;

            ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
            imagem.compress(Bitmap.CompressFormat.PNG, 100, baos1);

            bytes = baos1.toByteArray();
            fos = new FileOutputStream( dir );
            fos.write(bytes);
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**Metodo Responsavel por Salva a Foto
     */
//    public String convertFoto() {
//
//        Bitmap bitmap;
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        byte[] dadosimagem;
//
//        if (foto != null){
//            bitmap = BitmapFactory.decodeFile( new File( Environment.getExternalStorageDirectory().getAbsolutePath()) );
//            foto.compress(Bitmap.CompressFormat.JPEG, 70, baos);
//            dadosimagem = baos.toByteArray();
//        }
//
//        return Base64.encodeToString( dadosimagem, Base64.DEFAULT ) ;
//
//    }

    public void loadDadosPessoais() {

        SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleFormatter1 = new SimpleDateFormat("ddMMyyyy");

        usuario = sharedPreferences.recupraDadosPessoais();

        if (usuario != null) {
            nome.setText(usuario.getNome());
            email.setText(usuario.getEmail());
           // dataNascimento.setText(simpleFormatter1.format(simpleFormatter.parse(usuario.getDataNascimento())));
            telefone.setText(usuario.getTelefone());
            cep.setText(usuario.getCEP());
            cidade.setText(usuario.getCidade());
            bairro.setText(usuario.getBairro());
            endereco.setText(usuario.getEndereco());
            numero.setText(usuario.getNumero());
            complemento.setText(usuario.getComplemento());
        }

    }

//    public void saveDadosPessoais(View view){
//
//        SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat simpleFormatter1 = new SimpleDateFormat("ddMMyyyy");
//
//        usuario = new Usuario();
//
//        usuario.setFoto_URL( convertFoto() );
//        usuario.setNome(nome.getText().toString());
//        usuario.setEmail(email.getText().toString());
//
//        usuario.setDataNascimento( simpleFormatter.format( simpleFormatter1.parse(dataNascimento.getRawText().toString()) ).toString() );
//        usuario.setTelefone(telefone.getRawText().toString());
//        usuario.setCEP(cep.getRawText().toString());
//        usuario.setCidade(cidade.getText().toString());
//        usuario.setBairro(bairro.getText().toString());
//        usuario.setEndereco(endereco.getText().toString());
//        usuario.setNumero(numero.getText().toString());
//        usuario.setComplemento(complemento.getText().toString());
//
//
//        resolve.postUsuario( usuario ).enqueue(new Callback<Usuario>() {
//           @Override
//           public void onResponse (Call<Usuario> call, Response<Usuario> response) {
//
//               if ( response.isSuccessful() ){
//
//                   sharedPreferences.atualizaDadosPessoais( usuario );
//                   alertSalvamento("Atualizado com sucesso!");
//               }
//               else{
//                   alertSalvamento("Não foi possível atualizar.");
//               }
//
//           }
//
//           @Override
//           public void onFailure (Call<Usuario> call, Throwable t) {
//               alertSalvamento("Verifique as conexões com a Internet.");
//           }
//       });
//
//    }
//
    public void alertSalvamento(String mensagem){

        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();

    }

    public void salvaFotoFirebase(){

//        convertFoto();
//
//        StorageReference imagemRef =  storageReference.child("Imagens")
//                .child("Perfil")
//                .child(nomeUsuario)
//                .child("FotoPerfil.jpeg");
//
//        UploadTask uploadTask = imagemRef.putBytes( dadosimagem );
//
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getApplicationContext(),"Erro ao fazer Upload da Imagem",Toast.LENGTH_LONG).show();
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText(getApplicationContext(),"Sucesso fazer Upload da Imagem",Toast.LENGTH_LONG).show();
//
//                Uri url = taskSnapshot.getDownloadUrl();
//                AtualizaFotoUsuario( url );
//            }
//        });

    }

}