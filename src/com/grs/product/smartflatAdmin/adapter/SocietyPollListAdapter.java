package com.grs.product.smartflatAdmin.adapter;


import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.SmartFlatAdminApplication;
import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.asynctasks.UploadPollVoteTask;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.models.SocietyPollDetails;
import com.grs.product.smartflatAdmin.response.Response;
import com.grs.product.smartflatAdmin.utils.CustomProgressDialog;
import com.grs.product.smartflatAdmin.utils.NetworkDetector;
import com.grs.product.smartflatAdmin.utils.Utilities;

public class SocietyPollListAdapter   extends BaseAdapter {
	private Context context;
	private List<SocietyPollDetails> listSocietyPollDetails = new ArrayList<SocietyPollDetails>();
	SocietyPollDetails temp;
	int position1;
	String optionNumber = "";
	
	public SocietyPollListAdapter(Context context,
			List<SocietyPollDetails> listSocietyPollDetails) {
		super();
		this.context = context;
		this.listSocietyPollDetails = listSocietyPollDetails;
	}

	@Override
	public int getCount() {
		return listSocietyPollDetails.size();
	}

	@Override
	public Object getItem(int position) {
		return listSocietyPollDetails.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = infalInflater.inflate(R.layout.society_poll_list_item, null);
		}
		temp = listSocietyPollDetails.get(position);
		TextView textViewTopic = (TextView) rowView.findViewById(R.id.textViewTopic);
		textViewTopic.setText(temp.getmPollTopic());
		TextView textViewDetails = (TextView) rowView.findViewById(R.id.textViewDetails);
		textViewDetails.setText(temp.getmPollTopicDetails());
		
		Button buttonVote = (Button) rowView.findViewById(R.id.buttonVote);
		if(temp.getmPollOption1UpvotedFlatOwner().contains(SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences())
				||temp.getmPollOption2UpvotedFlatOwner().contains(SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences())
				||temp.getmPollOption3UpvotedFlatOwner().contains(SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences())
				||temp.getmPollOption4UpvotedFlatOwner().contains(SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences())){
			buttonVote.setVisibility(View.GONE);
		}
		
		
		final RadioGroup radioGroupOption = (RadioGroup) rowView.findViewById(R.id.radioGroupOption);
		final RadioButton radioButtonOptionOne = (RadioButton) rowView.findViewById(R.id.radioButtonOptionOne);
		radioButtonOptionOne.setText(temp.getmPollOption1());
		final RadioButton radioButtonOptionTwo = (RadioButton) rowView.findViewById(R.id.radioButtonOptionTwo);
		radioButtonOptionTwo.setText(temp.getmPollOption2());
		final RadioButton	 radioButtonOptionThree = (RadioButton) rowView.findViewById(R.id.radioButtonOptionThree);
		if(temp.getmPollOption3().equalsIgnoreCase("")){
			radioButtonOptionThree.setVisibility(View.GONE);	
		}else{
			radioButtonOptionThree.setText(temp.getmPollOption3());	
		}		
		final RadioButton	 radioButtonOptionFour = (RadioButton) rowView.findViewById(R.id.radioButtonOptionFour);
		if(temp.getmPollOption4().equalsIgnoreCase("")){
			radioButtonOptionFour.setVisibility(View.GONE);	
		}else{
			radioButtonOptionFour.setText(temp.getmPollOption4());
		}

		buttonVote.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!radioButtonOptionOne.isChecked() && !radioButtonOptionTwo.isChecked()
						&& !radioButtonOptionThree.isChecked() && !radioButtonOptionFour.isChecked())
				{
					Utilities.ShowAlertBox(context, "Message", "Please select option to vote");
				}else{
					
					switch (radioGroupOption.getCheckedRadioButtonId()) {
					case R.id.radioButtonOptionOne:
						optionNumber = "1";
						break;
						
					case R.id.radioButtonOptionTwo:
						optionNumber = "2";
						break;
						
					case R.id.radioButtonOptionThree:
						optionNumber = "3";
						break;
						
					case R.id.radioButtonOptionFour:
						optionNumber = "4";
						break;

					default:
						break;
					}
					temp = listSocietyPollDetails.get(position);
					position1 = position;
					uploadVote(temp,optionNumber);
				}			
			}
		});
		
		return rowView;
	}
	
	private void uploadVote(SocietyPollDetails societyPollDetails, String selectedOption)
	{	
		if(NetworkDetector.init(context).isNetworkAvailable()) 
		{
			new UploadPollVoteTask(context, new UploadPollVoteTaskListener(), societyPollDetails, selectedOption)
			.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} 
		else 
		{
			Utilities.ShowAlertBox(context, "Message", "Please check your Internet");		
		}					
	}
	
	public class UploadPollVoteTaskListener implements AsyncTaskCompleteListener<Response>{

		@Override
		public void onStarted() {
			CustomProgressDialog.showProgressDialog(context, "", false);
		}

		@Override
		public void onTaskComplete(Response result) {
			if (result != null) 
			{
				if (result.getStatus().equalsIgnoreCase("success")) 
				{
					Utilities.ShowAlertBox(context, "Message", "Vote Done");
					calculatePollVote(temp);
					listSocietyPollDetails.set(position1, temp);
					notifyDataSetChanged();
				}else{
					Utilities.ShowAlertBox(context, "Message", "Vote Fail");	
				}
			}	
		}

		@Override
		public void onStoped() {
			CustomProgressDialog.removeDialog();
		}

		@Override
		public void onStopedWithError(SmartFlatAdminError e) {
			CustomProgressDialog.removeDialog();
			Utilities.ShowAlertBox(context, "Error", "Server Error please try later");
		}
	}
	
	private void calculatePollVote(SocietyPollDetails details)
	{
		String[] getUsersVotedforOption1=null;
		String[] getUsersVotedforOption2 = null;
		String[] getUsersVotedforOption3=null;
		String[] getUsersVotedforOption4=null;
		if(!temp.getmPollOption1UpvotedFlatOwner().equalsIgnoreCase("")){
			getUsersVotedforOption1 = temp.getmPollOption1UpvotedFlatOwner().replace("?", "").split("##"); 
		}
		if(!temp.getmPollOption2UpvotedFlatOwner().equalsIgnoreCase("")){
			getUsersVotedforOption2 = temp.getmPollOption2UpvotedFlatOwner().replace("?", "").split("##"); 
		}
		if(!temp.getmPollOption3UpvotedFlatOwner().equalsIgnoreCase("")){
			getUsersVotedforOption3 = temp.getmPollOption3UpvotedFlatOwner().replace("?", "").split("##"); 
		}
		if(!temp.getmPollOption4UpvotedFlatOwner().equalsIgnoreCase("")){
			getUsersVotedforOption4 = temp.getmPollOption4UpvotedFlatOwner().replace("?", "").split("##"); 
		}
	
		int totalUsersVoted = (getUsersVotedforOption1!=null?getUsersVotedforOption1.length:0) + (getUsersVotedforOption2!=null?getUsersVotedforOption2.length:0)+(getUsersVotedforOption3!=null?getUsersVotedforOption3.length:0)+(getUsersVotedforOption4!=null?getUsersVotedforOption4.length:0);
		
		if(getUsersVotedforOption1!=null&& getUsersVotedforOption1.length>0)
		{
			double percentage = (getUsersVotedforOption1.length*100)/totalUsersVoted;
			percentage = Math.round(percentage);
			temp.setmPollOption1(temp.getmPollOption1()+"      "+percentage+"%");
		}
		
		if(getUsersVotedforOption2!=null&& getUsersVotedforOption2.length>0)
		{
			double percentage = (getUsersVotedforOption2.length*100)/totalUsersVoted;
			percentage = Math.round(percentage);
			temp.setmPollOption2(temp.getmPollOption2()+"      "+percentage+"%");
		}
		
		if(getUsersVotedforOption3!=null&& getUsersVotedforOption3.length>0)
		{
			double percentage = (getUsersVotedforOption3.length*100)/totalUsersVoted;
			percentage = Math.round(percentage);
			temp.setmPollOption3(temp.getmPollOption3()+"      "+percentage+"%");
		}
		
		if(getUsersVotedforOption4!=null&& getUsersVotedforOption4.length>0)
		{
			double percentage = (getUsersVotedforOption4.length*100)/totalUsersVoted;
			percentage = Math.round(percentage);
			temp.setmPollOption4(temp.getmPollOption4()+"      "+percentage+"%");
		}
	}	
}
