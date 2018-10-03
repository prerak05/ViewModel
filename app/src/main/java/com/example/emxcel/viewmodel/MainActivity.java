package com.example.emxcel.viewmodel;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.emxcel.viewmodel.database.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NoteViewModel viewModel;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private BottomSheetDialog insertDataDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View viewInsert = getLayoutInflater().inflate(R.layout.bottom_sheet_insert, null);
                insertDataDialog = new BottomSheetDialog(MainActivity.this);
                insertDataDialog.setContentView(viewInsert);
                insertDataDialog.setCancelable(true);
                final EditText etTitle = viewInsert.findViewById(R.id.etTitle);
                final EditText etPriority = viewInsert.findViewById(R.id.etPriority);
                final EditText etDescription = viewInsert.findViewById(R.id.etDescription);
                Button btnAdd = viewInsert.findViewById(R.id.btnAdd);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String sTitle = etTitle.getText().toString();
                        String sPriority = etPriority.getText().toString();
                        String sDescription = etDescription.getText().toString();

                        if (sTitle != null && sTitle.equals("")) {
                            etTitle.setError("Enter Title");
                            etTitle.requestFocus();
                        } else if (sPriority != null && sPriority.equals("")) {
                            etPriority.setError("Enter Priority");
                            etPriority.requestFocus();
                        } else if (sDescription != null && sDescription.equals("")) {
                            etDescription.setError("Enter Description");
                            etDescription.requestFocus();
                        } else {
                            viewModel.insert(new Note(sTitle, sDescription, Integer.parseInt(sPriority)));
                            insertDataDialog.dismiss();
                        }
                    }
                });

                insertDataDialog.show();

            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final NoteAdapter noteAdapter = new NoteAdapter(this);
        recyclerView.setAdapter(noteAdapter);
        viewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        viewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                noteAdapter.setNotes(notes);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miDeleteAll:
               viewModel.deleteAll();
               return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
