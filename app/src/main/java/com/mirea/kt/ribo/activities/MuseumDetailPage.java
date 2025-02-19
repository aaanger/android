package com.mirea.kt.ribo.activities;

import android.content.Intent;
import android.widget.Toast;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mirea.kt.ribo.R;

public class MuseumDetailPage extends AppCompatActivity {
    private ImageView museumImage;
    private TextView museumName, museumAddress, museumPhone, museumWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.museum_page);

        museumImage = findViewById(R.id.museum_image);
        museumName = findViewById(R.id.museum_name);
        museumAddress = findViewById(R.id.museum_address);
        museumPhone = findViewById(R.id.museum_phone);
        museumWebsite = findViewById(R.id.museum_website);

        Intent intent = getIntent();
        String name = intent.getStringExtra("museum_name");
        int imageResId = intent.getIntExtra("museum_image", 0);
        String address = intent.getStringExtra("address");
        String phone = intent.getStringExtra("phone");
        String website = intent.getStringExtra("website");

        museumName.setText(name);
        museumImage.setImageResource(imageResId);
        museumAddress.setText(address);
        museumPhone.setText(phone);
        museumWebsite.setText(website);

        museumAddress.setOnClickListener(v -> openMaps(address));

        museumPhone.setOnClickListener(v -> dialPhoneNumber(phone));

        museumWebsite.setOnClickListener(v -> openWebsite(website));
    }

    private void openMaps(String address) {
        try {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                Toast.makeText(this, "Google Maps не установлен", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка при открытии карт", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void dialPhoneNumber(String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }

    private void openWebsite(String url) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        webIntent.setData(Uri.parse(url));
        startActivity(webIntent);
    }
}