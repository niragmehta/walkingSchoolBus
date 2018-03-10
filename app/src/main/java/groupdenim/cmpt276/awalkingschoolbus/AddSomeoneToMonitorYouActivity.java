package groupdenim.cmpt276.awalkingschoolbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;
import java.util.Map;

public class AddSomeoneToMonitorYouActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_someone_to_monitor_you);


        final Map<String,User> masterMap=UserSingleton.getUserMap();
        final String currentUserEmail=UserSingleton.getCurrentUserEmail();
        final User currentUser=masterMap.get(currentUserEmail);

        final EditText emailInput=findViewById(R.id.tetxtEmailInput);
        Button back=findViewById(R.id.btnBack);
        Button confirm=findViewById(R.id.btnConfirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkEmailValidity(emailInput,currentUser,masterMap))
                    return;

                currentUser.getPeopleMonitoringUser().add(emailInput.getText().toString());


                //only implemented after checkEmailValidity works

                //adds current user to list of that particular user you want to be monitored by
                User someoneMonitoringCurrentUser=masterMap.get(emailInput.getText().toString());
                someoneMonitoringCurrentUser.getPeopleUserIsMonitoring().add(currentUserEmail);


                Intent intent=new Intent(AddSomeoneToMonitorYouActivity.this,MonitorActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddSomeoneToMonitorYouActivity.this,MonitorActivity.class);
                startActivity(intent);
            }
        });

    }

    public boolean checkEmailValidity(EditText emailInput,User currentUser,Map<String,User> masterMap)
    {
        //add code here to check if email exists from map/list or if already a part of user's list


        //checks if input email even exists
        User userExistenseCheck=masterMap.get(emailInput.getText().toString());
        if(userExistenseCheck==null)
            return false;


        //checks for duplicate within existing user's list
        List<String> list=currentUser.getPeopleMonitoringUser();
        for(int i=0;i<list.size();i++)
        {
            if(emailInput.getText().toString().equals(list.get(i)))
                return false;
        }


        return true;    //only for now
    }



}
