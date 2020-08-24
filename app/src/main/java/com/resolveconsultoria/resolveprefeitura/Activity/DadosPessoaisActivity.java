package com.resolveconsultoria.resolveprefeitura.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.resolveconsultoria.resolveprefeitura.R;
import com.santalu.maskedittext.MaskEditText;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class DadosPessoaisActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView foto;
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

    private static final int selecaoCamera = 10;
    private static final int selecaoGaleraia = 20;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_pessoais);

        inicializaComponentes ();
        requestCameraPermission();

        toolbar.setTitle( "Dados Pessoais" );
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
                    foto.setImageBitmap(imagem);
                    SalvaFoto(imagem);
                }

            }catch (Exception e ){
                e.printStackTrace();
            }
        }
    }

    private void inicializaComponentes () {

        toolbar        = findViewById( R.id.toolbar );
        foto           = findViewById( R.id.profile_image );
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

    public void SalvaFoto(Bitmap imagem){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos );
        byte[] dadosimagem = baos.toByteArray();

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