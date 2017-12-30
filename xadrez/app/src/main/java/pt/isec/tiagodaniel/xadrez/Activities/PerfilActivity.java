package pt.isec.tiagodaniel.xadrez.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import pt.isec.tiagodaniel.xadrez.Dialogs.ErrorDialog;
import pt.isec.tiagodaniel.xadrez.Exceptions.NullSharedPreferencesException;
import pt.isec.tiagodaniel.xadrez.Logic.Constantes;
import pt.isec.tiagodaniel.xadrez.Logic.Ferramentas;
import pt.isec.tiagodaniel.xadrez.R;

public class PerfilActivity extends Activity {
    private EditText mTxtNome;
    private Ferramentas mFerramentas;
    private String mPhotoPath;
    private ImageView mImvPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1234);
            }

            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 4321);
            }
        }

        try {
            this.mFerramentas = new Ferramentas(this);
            this.mTxtNome = findViewById(R.id.txtNome);
            this.mTxtNome.setText(this.mFerramentas.getSavedName());
            this.mImvPhoto = findViewById(R.id.imvPhoto);
            this.mPhotoPath = this.mFerramentas.getSavedPhotoPath();
            this.mFerramentas.setPic(this.mImvPhoto, this.mPhotoPath);
        } catch (NullSharedPreferencesException e) {
            ErrorDialog mErrorDialog = new ErrorDialog(e.toString());
            mErrorDialog.show(getFragmentManager(), Constantes.ERROR_DIALOG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSelecionarImagem(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 10);
    }

    public void onTirarFoto(View v) throws IOException {
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(name, Constantes.PHOTO_FORMAT, storageDir);

            this.mPhotoPath = image.getAbsolutePath();

            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));
            startActivityForResult(intent, 20);
        } catch (Exception e) {
            ErrorDialog mErrorDialog = new ErrorDialog(getString(R.string.take_photo));
            mErrorDialog.show(getFragmentManager(), Constantes.ERROR_DIALOG);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 10 && data != null && data.getData() != null) {
            Uri _uri = data.getData();
            if (_uri != null) {
                Cursor cursor = getContentResolver().query(_uri,
                        new String[]{android.provider.MediaStore.Images.ImageColumns.DATA},
                        null, null, null);
                cursor.moveToFirst();
                this.mPhotoPath = cursor.getString(0);

                this.mFerramentas.setPic(this.mImvPhoto, this.mPhotoPath);
                cursor.close();
            }
        }

        if (requestCode == 20 && resultCode == Activity.RESULT_OK) {
            this.mFerramentas.setPic(this.mImvPhoto, this.mPhotoPath);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        this.mFerramentas.saveName(this.mTxtNome.getText().toString());
        this.mFerramentas.saveFoto(this.mPhotoPath);
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = new MenuInflater(this);
        mi.inflate(R.menu.menu_perfil, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuPerfil) {
            Intent intent = new Intent(this, HistoricoActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}