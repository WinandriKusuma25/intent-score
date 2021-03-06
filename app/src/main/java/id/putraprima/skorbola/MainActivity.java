package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;
    private boolean isHome = true;
    private EditText homeTeam;
    private EditText awayTeam;
    private ImageView homeLogo;
    private ImageView awayLogo;
    private Uri homeUri;
    private Uri awayUri;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO
        homeTeam = findViewById(R.id.home_team);
        awayTeam = findViewById(R.id.away_team);
        homeLogo = findViewById(R.id.home_logo);
        awayLogo = findViewById(R.id.away_logo);


        //Fitur Main Activity
        //1. Validasi Input Home Team
        //2. Validasi Input Away Team
        //3. Ganti Logo Home Team
        //4. Ganti Logo Away Team
        //5. Next Button Pindah Ke MatchActivity

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED){
            return;
        }

        if (requestCode == GALLERY_REQUEST_CODE){
            if (isHome){
                if (data != null){
                    try {
                        homeUri = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), homeUri);
                        homeLogo.setImageBitmap(bitmap);
                    }catch (IOException e){
                        Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, e.getMessage());
                    }
                }
            }else{
                if (data != null){
                    try {
                        awayUri = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), awayUri);
                        awayLogo.setImageBitmap(bitmap);
                    }catch (IOException e){
                        Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
        }
    }

    private void selectImage(Context context) {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , GALLERY_REQUEST_CODE);
    }

    public void handleHomeLogo(View view) {
        isHome = true;
        selectImage(this);
    }

    public void handleAwayLogo(View view) {
        isHome = false;
        selectImage(this);
    }

    public void submitdata(View view) {
        String homeTeamName = homeTeam.getText().toString();
        String awayTeamName = awayTeam.getText().toString();
        String homeUriString = homeUri.toString();
        String awayUriString = awayUri.toString();

        boolean validate = homeTeamName.isEmpty()&&awayTeamName.isEmpty()&&homeUriString.isEmpty()&&awayUriString.isEmpty();


        if(homeTeamName.isEmpty()){
            Toast.makeText(this, "Entry home team name!", Toast.LENGTH_SHORT).show();
        }
        else if(awayTeamName.isEmpty()){
            Toast.makeText(this, "Entry away Team name!", Toast.LENGTH_SHORT).show();
        }
        else if(homeUriString==null){
            Toast.makeText(this, "Attach Home Team logo!", Toast.LENGTH_SHORT).show();
        }
        else if(awayUriString==null){
            Toast.makeText(this, "Attach Away Team logo!", Toast.LENGTH_SHORT).show();
        }
        else if(validate){
            Toast.makeText(this, "Fill all Team name!", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent i = new Intent(MainActivity.this, MatchActivity.class);
            i.putExtra("homeTeam", homeTeamName);
            i.putExtra("awayTeam", awayTeamName);
            i.putExtra("homeUri", homeUriString);
            i.putExtra("awayUri", awayUriString);
            startActivity(i);
        }

    }
}
