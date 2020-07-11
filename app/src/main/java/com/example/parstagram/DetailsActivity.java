package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.io.File;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    private TextView dvUsername;
    private ImageView dvImage;
    private TextView dvDate;
    private TextView dvDescription;
    private Post detailView;
    private ScrollView scrollView;
    private EditText comment;
    private Button submit;
    private Context context;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        context = this;
        dvUsername = findViewById(R.id.dvUsername);
        dvImage = findViewById(R.id.dvImage);
        dvDate = findViewById(R.id.dvTime);
        dvDescription = findViewById(R.id.dvDescription);
        scrollView = findViewById(R.id.dvScroll);
        submit = findViewById(R.id.dvbutton);
        comment = findViewById(R.id.editTextTextMultiLine);
        ll = findViewById(R.id.LL);

        detailView = Parcels.unwrap(getIntent().getParcelableExtra("post"));
        dvDescription.setText(detailView.getDescription());
        dvUsername.setText(detailView.getUser().getUsername());
        ParseFile image = detailView.getImage();


        if (image != null) {
            Glide.with(this).load(detailView.getImage().getUrl()).into(dvImage);
        }
        dvDate.setText(detailView.getKeyCreatedat());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveComment(comment.getText().toString(), ParseUser.getCurrentUser(), context, detailView);
            }
        });
        queryPosts(detailView, context);

    }

    public void queryPosts(Post post, final Context context) {
        ParseQuery<Comment> query = ParseQuery.getQuery(Comment.class);
        query.include(Comment.KEY_USER);
        query.whereEqualTo(Comment.KEY_POST, post);
        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> objects, ParseException e) {
                if (e != null) {
                    Log.e("Post", "issue with getting posts", e);
                }
                for (Comment comment : objects) {
                    Log.e("Post",comment.getComment());
                    TextView comment2 = new TextView(context);
                    comment2.setText(comment.getUser().getUsername() + " : "+comment.getComment());
                    ll.addView(comment2);

                }
            }
        });

    }

    private void saveComment(String description, ParseUser currentUser, Context context, Post post) {
        Log.i("saveComment", "saveComment: " + currentUser.getUsername());
        Comment comment2 = new Comment();
        comment2.setComment(description);
        comment2.setUser(currentUser);
        comment2.setKeyPost(post);
        comment2.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e("main activity", "Error while saving", e);

                }
                Log.i("TAG", "post was sucessfull");
                comment.setText("");
            }

        });
    }

}