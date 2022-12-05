package com.cookandroid.dakku;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sticker.StickerView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class EditTextFragment extends Fragment implements View.OnClickListener {
    public EditTextFragment() {

    }
    private SendEventListener sendEventListener;
    EditText editText;
    Button closeBtn, saveTxtBtn;
    Button colorBtn0, colorBtn1, colorBtn2;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof SendEventListener) {
            sendEventListener = (SendEventListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        sendEventListener = null;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_text, container, false);

        editText = root.findViewById(R.id.editTxt);
        closeBtn = root.findViewById(R.id.btnCloseTxtFragment);
        saveTxtBtn = root.findViewById(R.id.btnSaveTxt);
        colorBtn0 = root.findViewById(R.id.btnColor0);
        colorBtn1 = root.findViewById(R.id.btnColor1);
        colorBtn2 = root.findViewById(R.id.btnColor2);

        closeBtn.setOnClickListener(this);
        saveTxtBtn.setOnClickListener(this);
        colorBtn0.setOnClickListener(this);
        colorBtn1.setOnClickListener(this);
        colorBtn2.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnCloseTxtFragment:
            {
                //Toast.makeText(getActivity(), "btnCloseTxtFragment", Toast.LENGTH_SHORT).show();
                CloseThisFragment();
                break;
            }
            case R.id.btnSaveTxt:
            {
                sendEventListener.sendMessage(editText.getText().toString());
                //Toast.makeText(getActivity(), "btnSaveTxt", Toast.LENGTH_SHORT).show();
                CloseThisFragment();
                break;
            }
            case R.id.btnColor0:
            {
                sendEventListener.sendColor(Color.RED);
                break;
            }
            case R.id.btnColor1:
            {
                sendEventListener.sendColor(Color.GREEN);
                break;
            }
            case R.id.btnColor2:
            {
                sendEventListener.sendColor(Color.BLUE);
                break;
            }
        }
    }

    void CloseThisFragment()
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().remove(EditTextFragment.this).commit();
        fragmentManager.popBackStack();
    }
}