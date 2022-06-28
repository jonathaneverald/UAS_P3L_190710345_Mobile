package com.example.p3l;

import static com.android.volley.Request.Method.POST;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.p3l.Volley.api.TransaksiApi;
import com.example.p3l.Volley.models.LaporanDetailPendapatan;
import com.example.p3l.Volley.models.LaporanDetailPendapatanResponse;
import com.example.p3l.Volley.models.LaporanPerformaDriver;
import com.example.p3l.Volley.models.LaporanPerformaDriverResponse;
import com.example.p3l.Volley.models.LaporanSewaMobil;
import com.example.p3l.Volley.models.LaporanSewaMobilResponse;
import com.example.p3l.Volley.models.LaporanTopCustomer;
import com.example.p3l.Volley.models.LaporanTopCustomerResponse;
import com.example.p3l.Volley.models.LaporanTopDriver;
import com.example.p3l.Volley.models.LaporanTopDriverResponse;
import com.example.p3l.Volley.models.Tanggal;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LaporanActivity extends AppCompatActivity {
    private AutoCompleteTextView month, year;
    private MaterialButton confim, back;
    private LinearLayout layoutLoading;
    private RequestQueue queue;
    private LaporanDetailPendapatan[] laporanDetailPendapatan = null;
    private LaporanPerformaDriver[] laporanPerformaDriver = null;
    private LaporanSewaMobil[] laporanSewaMobil = null;
    private LaporanTopCustomer[] laporanTopCustomer = null;
    private LaporanTopDriver[] laporanTopDriver = null;

    private static final String[] BULAN_LIST = new String[]{"Januari", "Febuari",
            "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};

    private static final String[] TAHUN_LIST = new String[110];

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);

        queue = Volley.newRequestQueue(this);

        for(int i=0; i<110; i++) {
            TAHUN_LIST[i] = String.valueOf(i+1990);
        }

        confim = findViewById(R.id.btn_confirm);
        back = findViewById(R.id.btn_back);
        year = findViewById(R.id.ed_year);
        month = findViewById(R.id.ed_month);
        layoutLoading = findViewById(R.id.layout_loading);

        ArrayAdapter<String> adapterTahun =
                new ArrayAdapter<>(this, R.layout.item_list, TAHUN_LIST);
        year.setAdapter(adapterTahun);

        ArrayAdapter<String> adapterBulan =
                new ArrayAdapter<>(this, R.layout.item_list, BULAN_LIST);
        month.setAdapter(adapterBulan);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        confim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLaporan();
            }
        });
    }

    private Integer angkaBulan(String bulan) {
        if(bulan.equals("Januari")) {
            return 1;
        } else if(bulan.equals("Febuari")) {
            return 2;
        } else if(bulan.equals("Maret")) {
            return 3;
        } else if(bulan.equals("April")) {
            return 4;
        } else if(bulan.equals("Mei")) {
            return 5;
        } else if(bulan.equals("Juni")) {
            return 6;
        } else if(bulan.equals("Juli")) {
            return 7;
        } else if(bulan.equals("Agustus")) {
            return 8;
        } else if(bulan.equals("September")) {
            return 9;
        } else if(bulan.equals("Oktober")) {
            return 10;
        } else if(bulan.equals("November")) {
            return 11;
        } else if(bulan.equals("Desember")) {
            return 12;
        }
        return null;
    }

    private void getLaporan(){
        setLoading(true);

        String temp = "";
        if(!month.getText().toString().isEmpty())
            temp = String.valueOf(angkaBulan(month.getText().toString()));

        Tanggal tanggal = new Tanggal(
                year.getText().toString(),
                temp
        );

        StringRequest stringRequest = new StringRequest(POST,
                TransaksiApi.LAPORAN_URL + getIntent().getStringExtra("laporan"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();

                if(getIntent().getStringExtra("laporan").equals("laporan_sewa_mobil")) {
                    LaporanSewaMobilResponse laporanSewaMobilResponse =
                            gson.fromJson(response, LaporanSewaMobilResponse.class);

                    laporanSewaMobil = laporanSewaMobilResponse.getLaporanSewaMobil();

                    try {
                        cetakPdfLaporanSewaMobil();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else if(getIntent().getStringExtra("laporan").equals("laporan_detail_pendapatan")) {
                    LaporanDetailPendapatanResponse laporanDetailPendapatanResponse =
                            gson.fromJson(response, LaporanDetailPendapatanResponse.class);

                    laporanDetailPendapatan = laporanDetailPendapatanResponse.getLaporanDetailPendapatan();
                    try {
                        cetakPdfLaporanDetailPendapatan();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else if(getIntent().getStringExtra("laporan").equals("laporan_top_driver")) {
                    LaporanTopDriverResponse laporanTopDriverResponse =
                            gson.fromJson(response, LaporanTopDriverResponse.class);

                    laporanTopDriver = laporanTopDriverResponse.getLaporanTopDriver();
                    try {
                        cetakPdfLaporanTopDriver();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else if(getIntent().getStringExtra("laporan").equals("laporan_top_customer")) {
                    LaporanTopCustomerResponse laporanTopCustomerResponse =
                            gson.fromJson(response, LaporanTopCustomerResponse.class);

                    laporanTopCustomer = laporanTopCustomerResponse.getLaporanTopCustomer();
                    try {
                        cetakPdfLaporanTopCustomer();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else if(getIntent().getStringExtra("laporan").equals("laporan_performa_driver")) {
                    LaporanPerformaDriverResponse laporanPerformaDriverResponse =
                            gson.fromJson(response, LaporanPerformaDriverResponse.class);

                    laporanPerformaDriver = laporanPerformaDriverResponse.getLaporanPerformaDriver();
                    try {
                        cetakPdfLaporanPerformaDriver();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                }
                finish();
                setLoading(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);

                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(LaporanActivity.this,
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(LaporanActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");

                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                 /* Serialisasi data dari java object MahasiswaResponse
                 menjadi JSON string menggunakan Gson */
                String requestBody = gson.toJson(tanggal);

                return requestBody.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {

                return "application/json";
            }
        };
        queue.add(stringRequest);
    }

    private void setLoading(boolean isLoading) {
        if (isLoading) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            layoutLoading.setVisibility(View.VISIBLE);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            layoutLoading.setVisibility(View.INVISIBLE);
        }
    }

    private void previewPdf(File pdfFile) {
        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List<ResolveInfo> list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Uri uri;
            uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", pdfFile);
            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.setDataAndType(uri, "application/pdf");
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pdfIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            pdfIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            pdfIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            this.grantUriPermission(getPackageName(), uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(pdfIntent);
        }
    }

    private void cetakPdfLaporanSewaMobil() throws FileNotFoundException, DocumentException {
        File folder = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);

        if (!folder.exists()) {
            folder.mkdir();
        }

        Date currentTime = Calendar.getInstance().getTime();
        String pdfName = currentTime.getTime() + ".pdf";

        File pdfFile = new File(folder.getAbsolutePath(), pdfName);
        OutputStream outputStream = new FileOutputStream(pdfFile);

        com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // bagian header
        Paragraph judul = new Paragraph("LAPORAN PENYEWAAN MOBIL \n\n",
                new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK));

        judul.setAlignment(Element.ALIGN_CENTER);
        document.add(judul);

        // Buat tabel
        PdfPTable tables = new PdfPTable(new float[]{16, 8});

        // Settingan ukuran tabel
        tables.getDefaultCell().setFixedHeight(50);
        tables.setTotalWidth(PageSize.A4.getWidth());
        tables.setWidthPercentage(100);
        tables.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        PdfPCell cellSupplier = new PdfPCell();
        cellSupplier.setPaddingLeft(20);
        cellSupplier.setPaddingBottom(10);
        cellSupplier.setBorder(Rectangle.NO_BORDER);

        Paragraph kepada = new Paragraph("Kepada Yth: \n" + "Manager Atma Rental" + "\n",
                new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK));
        cellSupplier.addElement(kepada);
        tables.addCell(cellSupplier);

        Paragraph NomorTanggal = new Paragraph( "Alamat: " + "Yogyakarta" + "\n\n" +"Tanggal: " +
                new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(currentTime) + "\n",
                new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10, com.itextpdf.text.Font.NORMAL, BaseColor.BLACK));

        NomorTanggal.setPaddingTop(5);
        tables.addCell(NomorTanggal);
        document.add(tables);
        com.itextpdf.text.Font f = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10, com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
        Paragraph Pembuka = new Paragraph("\nBerikut merupakan penyewaan mobil Atma Rental pada bulan "+month.getText().toString()+" dan tahun "+year.getText().toString()+" : \n\n", f);
        Pembuka.setIndentationLeft(20);
        document.add(Pembuka);
        PdfPTable tableHeader = new PdfPTable(new float[]{6, 6, 6, 6});

        tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableHeader.getDefaultCell().setFixedHeight(30);
        tableHeader.setTotalWidth(PageSize.A4.getWidth());
        tableHeader.setWidthPercentage(100);

        // Setup Column
        PdfPCell h1 = new PdfPCell(new Phrase("Tipe Mobil"));
        h1.setHorizontalAlignment(Element.ALIGN_CENTER);
        h1.setPaddingBottom(5);
        h1.setPaddingLeft(3);
        PdfPCell h2 = new PdfPCell(new Phrase("Nama Mobil"));
        h2.setHorizontalAlignment(Element.ALIGN_CENTER);
        h2.setPaddingBottom(5);
        h2.setPaddingLeft(3);
        PdfPCell h3 = new PdfPCell(new Phrase("Jumlah Transaksi"));
        h3.setHorizontalAlignment(Element.ALIGN_CENTER);
        h3.setPaddingBottom(5);
        h3.setPaddingLeft(3);
        PdfPCell h4 = new PdfPCell(new Phrase("Pendapatan"));
        h4.setHorizontalAlignment(Element.ALIGN_CENTER);
        h4.setPaddingBottom(5);
        h4.setPaddingLeft(3);

        tableHeader.addCell(h1);
        tableHeader.addCell(h2);
        tableHeader.addCell(h3);
        tableHeader.addCell(h4);

        // Beri warna untuk kolumn
        for (PdfPCell cells : tableHeader.getRow(0).getCells()) {
            cells.setBackgroundColor(BaseColor.GREEN);
        }

        document.add(tableHeader);
        PdfPTable tableData = new PdfPTable(new float[]{6, 6, 6, 6});

        tableData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        tableData.getDefaultCell().setPaddingLeft(3);
        tableData.getDefaultCell().setFixedHeight(30);
        tableData.setTotalWidth(PageSize.A4.getWidth());
        tableData.setWidthPercentage(100);
        tableData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        // masukan data pegawai jadi baris
        for (int i=0; i<laporanSewaMobil.length; i++) {
            tableData.addCell(laporanSewaMobil[i].getTipe_mobil());
            tableData.addCell(laporanSewaMobil[i].getNama_mobil_sewa());
            tableData.addCell(String.valueOf(laporanSewaMobil[i].getJumlah_peminjaman()));
            tableData.addCell(String.valueOf((int)laporanSewaMobil[i].getPendapatan()));
        }

        document.add(tableData);

        com.itextpdf.text.Font h = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10, com.itextpdf.text.Font.NORMAL);
        String tglDicetak = currentTime.toLocaleString();
        Paragraph P = new Paragraph("\nDicetak tanggal " + tglDicetak, h);
        P.setAlignment(Element.ALIGN_RIGHT);
        document.add(P);
        document.close();
        previewPdf(pdfFile);
        Toast.makeText(this, "PDF berhasil dibuat", Toast.LENGTH_SHORT).show();
    }

    private void cetakPdfLaporanDetailPendapatan() throws FileNotFoundException, DocumentException {
        File folder = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);

        if (!folder.exists()) {
            folder.mkdir();
        }

        Date currentTime = Calendar.getInstance().getTime();
        String pdfName = currentTime.getTime() + ".pdf";

        File pdfFile = new File(folder.getAbsolutePath(), pdfName);
        OutputStream outputStream = new FileOutputStream(pdfFile);

        com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // bagian header
        Paragraph judul = new Paragraph("LAPORAN DETAIL PENDAPATAN \n\n",
                new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK));

        judul.setAlignment(Element.ALIGN_CENTER);
        document.add(judul);

        // Buat tabel
        PdfPTable tables = new PdfPTable(new float[]{16, 8});

        // Settingan ukuran tabel
        tables.getDefaultCell().setFixedHeight(50);
        tables.setTotalWidth(PageSize.A4.getWidth());
        tables.setWidthPercentage(100);
        tables.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        PdfPCell cellSupplier = new PdfPCell();
        cellSupplier.setPaddingLeft(20);
        cellSupplier.setPaddingBottom(10);
        cellSupplier.setBorder(Rectangle.NO_BORDER);

        Paragraph kepada = new Paragraph("Kepada Yth: \n" + "Manager Atma Rental" + "\n",
                new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK));
        cellSupplier.addElement(kepada);
        tables.addCell(cellSupplier);

        Paragraph NomorTanggal = new Paragraph( "Alamat: " + "Yogyakarta" + "\n\n" +"Tanggal: " +
                new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(currentTime) + "\n",
                new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10, com.itextpdf.text.Font.NORMAL, BaseColor.BLACK));

        NomorTanggal.setPaddingTop(5);
        tables.addCell(NomorTanggal);
        document.add(tables);
        com.itextpdf.text.Font f = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10, com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
        Paragraph Pembuka = new Paragraph("\nBerikut merupakan detail pendapatan Atma Rental pada bulan "+month.getText().toString()+" dan tahun "+year.getText().toString()+" : \n\n", f);
        Pembuka.setIndentationLeft(20);
        document.add(Pembuka);
        PdfPTable tableHeader = new PdfPTable(new float[]{10, 5, 10, 5, 5});

        tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableHeader.getDefaultCell().setFixedHeight(30);
        tableHeader.setTotalWidth(PageSize.A4.getWidth());
        tableHeader.setWidthPercentage(100);

        // Setup Column
        PdfPCell h1 = new PdfPCell(new Phrase("Nama Customer"));
        h1.setHorizontalAlignment(Element.ALIGN_CENTER);
        h1.setPaddingBottom(5);
        h1.setPaddingLeft(3);
        PdfPCell h2 = new PdfPCell(new Phrase("Nama Mobil"));
        h2.setHorizontalAlignment(Element.ALIGN_CENTER);
        h2.setPaddingBottom(5);
        h2.setPaddingLeft(3);
        PdfPCell h3 = new PdfPCell(new Phrase("Jenis Penyewaan"));
        h3.setHorizontalAlignment(Element.ALIGN_CENTER);
        h3.setPaddingBottom(5);
        h3.setPaddingLeft(3);
        PdfPCell h4 = new PdfPCell(new Phrase("Jumlah Transaksi"));
        h4.setHorizontalAlignment(Element.ALIGN_CENTER);
        h4.setPaddingBottom(5);
        h4.setPaddingLeft(3);
        PdfPCell h5 = new PdfPCell(new Phrase("Pendapatan"));
        h5.setHorizontalAlignment(Element.ALIGN_CENTER);
        h5.setPaddingBottom(5);
        h5.setPaddingLeft(3);

        tableHeader.addCell(h1);
        tableHeader.addCell(h2);
        tableHeader.addCell(h3);
        tableHeader.addCell(h4);
        tableHeader.addCell(h5);

        // Beri warna untuk kolumn
        for (PdfPCell cells : tableHeader.getRow(0).getCells()) {
            cells.setBackgroundColor(BaseColor.GREEN);
        }

        document.add(tableHeader);
        PdfPTable tableData = new PdfPTable(new float[]{10, 5, 10, 5, 5});

        tableData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        tableData.getDefaultCell().setPaddingLeft(3);
        tableData.getDefaultCell().setFixedHeight(30);
        tableData.setTotalWidth(PageSize.A4.getWidth());
        tableData.setWidthPercentage(100);
        tableData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        // masukan data pegawai jadi baris
        for (int i=0; i<laporanDetailPendapatan.length; i++) {
            tableData.addCell(laporanDetailPendapatan[i].getNama_customer());
            tableData.addCell(laporanDetailPendapatan[i].getNama_mobil_sewa());
            tableData.addCell(laporanDetailPendapatan[i].getJenis_penyewaan());
            tableData.addCell(String.valueOf(laporanDetailPendapatan[i].getJumlah_peminjaman()));
            tableData.addCell(String.valueOf((int)laporanDetailPendapatan[i].getPendapatan()));
        }

        document.add(tableData);

        com.itextpdf.text.Font h = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10, com.itextpdf.text.Font.NORMAL);
        String tglDicetak = currentTime.toLocaleString();
        Paragraph P = new Paragraph("\nDicetak tanggal " + tglDicetak, h);
        P.setAlignment(Element.ALIGN_RIGHT);
        document.add(P);
        document.close();
        previewPdf(pdfFile);
        Toast.makeText(this, "PDF berhasil dibuat", Toast.LENGTH_SHORT).show();
    }

    private void cetakPdfLaporanTopDriver() throws FileNotFoundException, DocumentException {
        File folder = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);

        if (!folder.exists()) {
            folder.mkdir();
        }

        Date currentTime = Calendar.getInstance().getTime();
        String pdfName = currentTime.getTime() + ".pdf";

        File pdfFile = new File(folder.getAbsolutePath(), pdfName);
        OutputStream outputStream = new FileOutputStream(pdfFile);

        com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // bagian header
        Paragraph judul = new Paragraph("LAPORAN 5 DRIVER DENGAN TRANSAKSI TERBANYAK \n\n",
                new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK));

        judul.setAlignment(Element.ALIGN_CENTER);
        document.add(judul);

        // Buat tabel
        PdfPTable tables = new PdfPTable(new float[]{16, 8});

        // Settingan ukuran tabel
        tables.getDefaultCell().setFixedHeight(50);
        tables.setTotalWidth(PageSize.A4.getWidth());
        tables.setWidthPercentage(100);
        tables.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        PdfPCell cellSupplier = new PdfPCell();
        cellSupplier.setPaddingLeft(20);
        cellSupplier.setPaddingBottom(10);
        cellSupplier.setBorder(Rectangle.NO_BORDER);

        Paragraph kepada = new Paragraph("Kepada Yth: \n" + "Manager Atma Rental" + "\n",
                new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK));
        cellSupplier.addElement(kepada);
        tables.addCell(cellSupplier);

        Paragraph NomorTanggal = new Paragraph( "Alamat: " + "Yogyakarta" + "\n\n" +"Tanggal: " +
                new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(currentTime) + "\n",
                new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10, com.itextpdf.text.Font.NORMAL, BaseColor.BLACK));

        NomorTanggal.setPaddingTop(5);
        tables.addCell(NomorTanggal);
        document.add(tables);
        com.itextpdf.text.Font f = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10, com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
        Paragraph Pembuka = new Paragraph("\nBerikut merupakan 5 driver dengan transaksi terbanyak Atma Rental pada bulan "+month.getText().toString()+" dan tahun "+year.getText().toString()+" : \n\n", f);
        Pembuka.setIndentationLeft(20);
        document.add(Pembuka);
        PdfPTable tableHeader = new PdfPTable(new float[]{8, 8, 8});

        tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableHeader.getDefaultCell().setFixedHeight(30);
        tableHeader.setTotalWidth(PageSize.A4.getWidth());
        tableHeader.setWidthPercentage(100);

        // Setup Column
        PdfPCell h1 = new PdfPCell(new Phrase("ID Driver"));
        h1.setHorizontalAlignment(Element.ALIGN_CENTER);
        h1.setPaddingBottom(5);
        h1.setPaddingLeft(3);
        PdfPCell h2 = new PdfPCell(new Phrase("Nama Driver"));
        h2.setHorizontalAlignment(Element.ALIGN_CENTER);
        h2.setPaddingBottom(5);
        h2.setPaddingLeft(3);
        PdfPCell h3 = new PdfPCell(new Phrase("Jumlah Transaksi"));
        h3.setHorizontalAlignment(Element.ALIGN_CENTER);
        h3.setPaddingBottom(5);
        h3.setPaddingLeft(3);

        tableHeader.addCell(h1);
        tableHeader.addCell(h2);
        tableHeader.addCell(h3);

        // Beri warna untuk kolumn
        for (PdfPCell cells : tableHeader.getRow(0).getCells()) {
            cells.setBackgroundColor(BaseColor.GREEN);
        }

        document.add(tableHeader);
        PdfPTable tableData = new PdfPTable(new float[]{8, 8, 8});

        tableData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        tableData.getDefaultCell().setPaddingLeft(3);
        tableData.getDefaultCell().setFixedHeight(30);
        tableData.setTotalWidth(PageSize.A4.getWidth());
        tableData.setWidthPercentage(100);
        tableData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        // masukan data pegawai jadi baris
        for (int i=0; i<laporanTopDriver.length; i++) {
            tableData.addCell(laporanTopDriver[i].getFormat_id());
            tableData.addCell(laporanTopDriver[i].getNama_driver());
            tableData.addCell(String.valueOf(laporanTopDriver[i].getJumlah_peminjaman()));
        }

        document.add(tableData);

        com.itextpdf.text.Font h = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10, com.itextpdf.text.Font.NORMAL);
        String tglDicetak = currentTime.toLocaleString();
        Paragraph P = new Paragraph("\nDicetak tanggal " + tglDicetak, h);
        P.setAlignment(Element.ALIGN_RIGHT);
        document.add(P);
        document.close();
        previewPdf(pdfFile);
        Toast.makeText(this, "PDF berhasil dibuat", Toast.LENGTH_SHORT).show();
    }

    private void cetakPdfLaporanPerformaDriver() throws FileNotFoundException, DocumentException {
        File folder = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);

        if (!folder.exists()) {
            folder.mkdir();
        }

        Date currentTime = Calendar.getInstance().getTime();
        String pdfName = currentTime.getTime() + ".pdf";

        File pdfFile = new File(folder.getAbsolutePath(), pdfName);
        OutputStream outputStream = new FileOutputStream(pdfFile);

        com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // bagian header
        Paragraph judul = new Paragraph("LAPORAN PERFORMA DRIVER \n\n",
                new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK));

        judul.setAlignment(Element.ALIGN_CENTER);
        document.add(judul);

        // Buat tabel
        PdfPTable tables = new PdfPTable(new float[]{16, 8});

        // Settingan ukuran tabel
        tables.getDefaultCell().setFixedHeight(50);
        tables.setTotalWidth(PageSize.A4.getWidth());
        tables.setWidthPercentage(100);
        tables.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        PdfPCell cellSupplier = new PdfPCell();
        cellSupplier.setPaddingLeft(20);
        cellSupplier.setPaddingBottom(10);
        cellSupplier.setBorder(Rectangle.NO_BORDER);

        Paragraph kepada = new Paragraph("Kepada Yth: \n" + "Manager Atma Rental" + "\n",
                new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK));
        cellSupplier.addElement(kepada);
        tables.addCell(cellSupplier);

        Paragraph NomorTanggal = new Paragraph( "Alamat: " + "Yogyakarta" + "\n\n" +"Tanggal: " +
                new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(currentTime) + "\n",
                new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10, com.itextpdf.text.Font.NORMAL, BaseColor.BLACK));

        NomorTanggal.setPaddingTop(5);
        tables.addCell(NomorTanggal);
        document.add(tables);
        com.itextpdf.text.Font f = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10, com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
        Paragraph Pembuka = new Paragraph("\nBerikut merupakan performa driver Atma Rental pada bulan "+month.getText().toString()+" dan tahun "+year.getText().toString()+" : \n\n", f);
        Pembuka.setIndentationLeft(20);
        document.add(Pembuka);
        PdfPTable tableHeader = new PdfPTable(new float[]{6, 6, 6, 6});

        tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableHeader.getDefaultCell().setFixedHeight(30);
        tableHeader.setTotalWidth(PageSize.A4.getWidth());
        tableHeader.setWidthPercentage(100);

        // Setup Column
        PdfPCell h1 = new PdfPCell(new Phrase("ID Driver"));
        h1.setHorizontalAlignment(Element.ALIGN_CENTER);
        h1.setPaddingBottom(5);
        h1.setPaddingLeft(3);
        PdfPCell h2 = new PdfPCell(new Phrase("Nama Driver"));
        h2.setHorizontalAlignment(Element.ALIGN_CENTER);
        h2.setPaddingBottom(5);
        h2.setPaddingLeft(3);
        PdfPCell h3 = new PdfPCell(new Phrase("Jumlah Transaksi"));
        h3.setHorizontalAlignment(Element.ALIGN_CENTER);
        h3.setPaddingBottom(5);
        h3.setPaddingLeft(3);
        PdfPCell h4 = new PdfPCell(new Phrase("Rerata Rating"));
        h4.setHorizontalAlignment(Element.ALIGN_CENTER);
        h4.setPaddingBottom(5);
        h4.setPaddingLeft(3);

        tableHeader.addCell(h1);
        tableHeader.addCell(h2);
        tableHeader.addCell(h3);
        tableHeader.addCell(h4);

        // Beri warna untuk kolumn
        for (PdfPCell cells : tableHeader.getRow(0).getCells()) {
            cells.setBackgroundColor(BaseColor.GREEN);
        }

        document.add(tableHeader);
        PdfPTable tableData = new PdfPTable(new float[]{6, 6, 6, 6});

        tableData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        tableData.getDefaultCell().setPaddingLeft(3);
        tableData.getDefaultCell().setFixedHeight(30);
        tableData.setTotalWidth(PageSize.A4.getWidth());
        tableData.setWidthPercentage(100);
        tableData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        // masukan data pegawai jadi baris
        for (int i=0; i<laporanPerformaDriver.length; i++) {
            tableData.addCell(laporanPerformaDriver[i].getFormat_id());
            tableData.addCell(laporanPerformaDriver[i].getNama_driver());
            tableData.addCell(String.valueOf(laporanPerformaDriver[i].getJumlah_peminjaman()));
            tableData.addCell(String.format("%.2f",laporanPerformaDriver[i].getRerata_rating()));
        }

        document.add(tableData);

        com.itextpdf.text.Font h = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10, com.itextpdf.text.Font.NORMAL);
        String tglDicetak = currentTime.toLocaleString();
        Paragraph P = new Paragraph("\nDicetak tanggal " + tglDicetak, h);
        P.setAlignment(Element.ALIGN_RIGHT);
        document.add(P);
        document.close();
        previewPdf(pdfFile);
        Toast.makeText(this, "PDF berhasil dibuat", Toast.LENGTH_SHORT).show();
    }

    private void cetakPdfLaporanTopCustomer() throws FileNotFoundException, DocumentException {
        File folder = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);

        if (!folder.exists()) {
            folder.mkdir();
        }

        Date currentTime = Calendar.getInstance().getTime();
        String pdfName = currentTime.getTime() + ".pdf";

        File pdfFile = new File(folder.getAbsolutePath(), pdfName);
        OutputStream outputStream = new FileOutputStream(pdfFile);

        com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // bagian header
        Paragraph judul = new Paragraph("LAPORAN 5 CUSTOMER DENGAN TRANSAKSI TERBANYAK \n\n",
                new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK));

        judul.setAlignment(Element.ALIGN_CENTER);
        document.add(judul);

        // Buat tabel
        PdfPTable tables = new PdfPTable(new float[]{16, 8});

        // Settingan ukuran tabel
        tables.getDefaultCell().setFixedHeight(50);
        tables.setTotalWidth(PageSize.A4.getWidth());
        tables.setWidthPercentage(100);
        tables.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        PdfPCell cellSupplier = new PdfPCell();
        cellSupplier.setPaddingLeft(20);
        cellSupplier.setPaddingBottom(10);
        cellSupplier.setBorder(Rectangle.NO_BORDER);

        Paragraph kepada = new Paragraph("Kepada Yth: \n" + "Manager Atma Rental" + "\n",
                new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK));
        cellSupplier.addElement(kepada);
        tables.addCell(cellSupplier);

        Paragraph NomorTanggal = new Paragraph( "Alamat: " + "Yogyakarta" + "\n\n" +"Tanggal: " +
                new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(currentTime) + "\n",
                new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10, com.itextpdf.text.Font.NORMAL, BaseColor.BLACK));

        NomorTanggal.setPaddingTop(5);
        tables.addCell(NomorTanggal);
        document.add(tables);
        com.itextpdf.text.Font f = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10, com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
        Paragraph Pembuka = new Paragraph("\nBerikut merupakan 5 customer dengan transaksi terbanyak Atma Rental pada bulan "+month.getText().toString()+" dan tahun "+year.getText().toString()+" : \n\n", f);
        Pembuka.setIndentationLeft(20);
        document.add(Pembuka);
        PdfPTable tableHeader = new PdfPTable(new float[]{8, 5});

        tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableHeader.getDefaultCell().setFixedHeight(30);
        tableHeader.setTotalWidth(PageSize.A4.getWidth());
        tableHeader.setWidthPercentage(100);

        // Setup Column
        PdfPCell h1 = new PdfPCell(new Phrase("Nama Customer"));
        h1.setHorizontalAlignment(Element.ALIGN_CENTER);
        h1.setPaddingBottom(5);
        h1.setPaddingLeft(3);
        PdfPCell h2 = new PdfPCell(new Phrase("Jumlah Transaksi"));
        h2.setHorizontalAlignment(Element.ALIGN_CENTER);
        h2.setPaddingBottom(5);
        h2.setPaddingLeft(3);

        tableHeader.addCell(h1);
        tableHeader.addCell(h2);

        // Beri warna untuk kolumn
        for (PdfPCell cells : tableHeader.getRow(0).getCells()) {
            cells.setBackgroundColor(BaseColor.GREEN);
        }

        document.add(tableHeader);
        PdfPTable tableData = new PdfPTable(new float[]{8, 5});

        tableData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        tableData.getDefaultCell().setPaddingLeft(3);
        tableData.getDefaultCell().setFixedHeight(30);
        tableData.setTotalWidth(PageSize.A4.getWidth());
        tableData.setWidthPercentage(100);
        tableData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        // masukan data pegawai jadi baris
        for (int i=0; i<laporanTopCustomer.length; i++) {
            tableData.addCell(laporanTopCustomer[i].getNama_customer());
            tableData.addCell(String.valueOf(laporanTopCustomer[i].getJumlah_peminjaman()));
        }

        document.add(tableData);

        com.itextpdf.text.Font h = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10, com.itextpdf.text.Font.NORMAL);
        String tglDicetak = currentTime.toLocaleString();
        Paragraph P = new Paragraph("\nDicetak tanggal " + tglDicetak, h);
        P.setAlignment(Element.ALIGN_RIGHT);
        document.add(P);
        document.close();
        previewPdf(pdfFile);
        Toast.makeText(this, "PDF berhasil dibuat", Toast.LENGTH_SHORT).show();
    }
}