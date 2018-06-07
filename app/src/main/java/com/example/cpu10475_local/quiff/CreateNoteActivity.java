package com.example.cpu10475_local.quiff;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cpu10475_local.quiff.adapter.CustomFragmentPagerAdapter;

import com.example.cpu10475_local.quiff.model.Note;

public class CreateNoteActivity extends AppCompatActivity {
    ViewPager viewPager;
    //PaperAdapter paperAdapter;
    LinearLayout dotsLayout;
  //  int[] layouts;
    TextView[] dots;
    Note note;
    boolean isEdit = false;
    private static final String keyNote = "keyNote";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnote);
        init();
        if(savedInstanceState!=null)
            note = (Note) savedInstanceState.getSerializable(keyNote);
    }

    private void init() {
        //layouts = new int[]{R.layout.layout_add1,R.layout.layout_add2,R.layout.layout_add3};

        viewPager = findViewById(R.id.viewPager);
        dotsLayout = findViewById(R.id.dotsLayout);
        addBottomDots(0);
        note = (Note) getIntent().getSerializableExtra("note");
        if(note!=null)
            isEdit = true;
       // paperAdapter = new PaperAdapter(layouts,this, note);
        viewPager.setAdapter(new CustomFragmentPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[3];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    public Note getNote() {
        return note;
    }
    public void setNote(Note note)
    {
        this.note = note;
    }
    public boolean isEdit()
    {
        return isEdit;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(keyNote,note);
    }
    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() ==0)
            super.onBackPressed();
        else if(viewPager.getCurrentItem()==1)
            viewPager.setCurrentItem(0);
        else if(viewPager.getCurrentItem()==2)
            viewPager.setCurrentItem(1);

    }
}
