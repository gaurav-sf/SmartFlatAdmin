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
	private SocietyPollDetails temp1 = null;
	int position1;
	String optionNumber = "";
	ItemHolder itemHolder = null;
	
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
	
	public static class ItemHolder {
		public TextView empName;
		public RadioButton radioButton;
		public TextView textViewTopic;
		public TextView textViewDetails;
		public RadioGroup radioGroupOption;
		public RadioButton radioButtonOptionOne;
		public RadioButton radioButtonOptionTwo;
		public RadioButton radioButtonOptionThree;
		public RadioButton radioButtonOptionFour;
		public Button buttonVote;
	}

/*	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = infalInflater.inflate(R.layout.society_poll_list_item, null);
		}
		SocietyPollDetails temp = listSocietyPollDetails.get(position);
		TextView textViewTopic = (TextView) rowView.findViewById(R.id.textViewTopic);
		textViewTopic.setText(temp.getmPollTopic());
		TextView textViewDetails = (TextView) rowView.findViewById(R.id.textViewDetails);
		textViewDetails.setText(temp.getmPollTopicDetails());
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
		Button buttonVote = (Button) rowView.findViewById(R.id.buttonVote);
		if(temp.getmPollOption1UpvotedFlatOwner().contains(SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences())
				||temp.getmPollOption2UpvotedFlatOwner().contains(SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences())
				||temp.getmPollOption3UpvotedFlatOwner().contains(SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences())
				||temp.getmPollOption4UpvotedFlatOwner().contains(SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences())){
			buttonVote.setVisibility(View.GONE);
			temp1 = listSocietyPollDetails.get(position);
			position1 = position;
			calculatePollVote(temp1);
			listSocietyPollDetails.set(position1, temp1);
			notifyDataSetChanged();

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
					temp1 = listSocietyPollDetails.get(position);
					position1 = position;
					uploadVote(temp1,optionNumber);
				}			
			}
		});
		
		return rowView;
	}*/
	
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = infalInflater.inflate(R.layout.society_poll_list_item, parent);
			itemHolder = new ItemHolder();
			itemHolder.textViewTopic = (TextView) rowView.findViewById(R.id.textViewTopic);
			itemHolder.textViewDetails = (TextView) rowView.findViewById(R.id.textViewDetails);
			itemHolder.radioGroupOption = (RadioGroup) rowView.findViewById(R.id.radioGroupOption);
			itemHolder.radioButtonOptionOne = (RadioButton) rowView.findViewById(R.id.radioButtonOptionOne);
			itemHolder.radioButtonOptionTwo = (RadioButton) rowView.findViewById(R.id.radioButtonOptionTwo);
			itemHolder.radioButtonOptionThree = (RadioButton) rowView.findViewById(R.id.radioButtonOptionThree);
			itemHolder.radioButtonOptionFour = (RadioButton) rowView.findViewById(R.id.radioButtonOptionFour);
			itemHolder.buttonVote = (Button) rowView.findViewById(R.id.buttonVote);
			rowView.setTag(itemHolder);
		}else{
			itemHolder = (ItemHolder) rowView.getTag();
		}
		SocietyPollDetails temp = listSocietyPollDetails.get(position);		
		itemHolder.textViewTopic.setText(temp.getmPollTopic());		
		itemHolder.textViewDetails.setText(temp.getmPollTopicDetails());	 
		itemHolder.radioButtonOptionOne.setText(temp.getmPollOption1());		 
		itemHolder.radioButtonOptionTwo.setText(temp.getmPollOption2());	 
		if(temp.getmPollOption3().equalsIgnoreCase("")){
			itemHolder.radioButtonOptionThree.setVisibility(View.GONE);	
		}else{
			itemHolder.radioButtonOptionThree.setText(temp.getmPollOption3());	
		}		
		 
		if(temp.getmPollOption4().equalsIgnoreCase("")){
			itemHolder.radioButtonOptionFour.setVisibility(View.GONE);	
		}else{
			itemHolder.radioButtonOptionFour.setText(temp.getmPollOption4());
		}
		
		if(temp.getmPollOption1UpvotedFlatOwner().contains(SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences())
				||temp.getmPollOption2UpvotedFlatOwner().contains(SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences())
				||temp.getmPollOption3UpvotedFlatOwner().contains(SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences())
				||temp.getmPollOption4UpvotedFlatOwner().contains(SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences())){
			itemHolder.buttonVote.setVisibility(View.GONE);
			temp1 = listSocietyPollDetails.get(position);
			position1 = position;
			calculatePollVote(temp1);
			listSocietyPollDetails.set(position1, temp1);
			notifyDataSetChanged();

		}
		itemHolder.buttonVote.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!itemHolder.radioButtonOptionOne.isChecked() && !itemHolder.radioButtonOptionTwo.isChecked()
						&& !itemHolder.radioButtonOptionThree.isChecked() && !itemHolder.radioButtonOptionFour.isChecked())
				{
					Utilities.ShowAlertBox(context, "Message", "Please select option to vote");
				}else{
					
					switch (itemHolder.radioGroupOption.getCheckedRadioButtonId()) {
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
					temp1 = listSocietyPollDetails.get(position);
					position1 = position;
					uploadVote(temp1,optionNumber);
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
					calculatePollVote(temp1);
					listSocietyPollDetails.set(position1, temp1);
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
		if(!temp1.getmPollOption1UpvotedFlatOwner().equalsIgnoreCase("")){
			getUsersVotedforOption1 = temp1.getmPollOption1UpvotedFlatOwner().replace("?", "").split("##"); 
		}
		if(!temp1.getmPollOption2UpvotedFlatOwner().equalsIgnoreCase("")){
			getUsersVotedforOption2 = temp1.getmPollOption2UpvotedFlatOwner().replace("?", "").split("##"); 
		}
		if(!temp1.getmPollOption3UpvotedFlatOwner().equalsIgnoreCase("")){
			getUsersVotedforOption3 = temp1.getmPollOption3UpvotedFlatOwner().replace("?", "").split("##"); 
		}
		if(!temp1.getmPollOption4UpvotedFlatOwner().equalsIgnoreCase("")){
			getUsersVotedforOption4 = temp1.getmPollOption4UpvotedFlatOwner().replace("?", "").split("##"); 
		}
	
		int totalUsersVoted = (getUsersVotedforOption1!=null?getUsersVotedforOption1.length:0) + (getUsersVotedforOption2!=null?getUsersVotedforOption2.length:0)+(getUsersVotedforOption3!=null?getUsersVotedforOption3.length:0)+(getUsersVotedforOption4!=null?getUsersVotedforOption4.length:0);
		
		if(getUsersVotedforOption1!=null&& getUsersVotedforOption1.length>0)
		{
			double percentage = (getUsersVotedforOption1.length*100)/totalUsersVoted;
			percentage = Math.round(percentage);
			temp1.setmPollOption1(temp1.getmPollOption1()+"      "+percentage+"%");
		}
		
		if(getUsersVotedforOption2!=null&& getUsersVotedforOption2.length>0)
		{
			double percentage = (getUsersVotedforOption2.length*100)/totalUsersVoted;
			percentage = Math.round(percentage);
			temp1.setmPollOption2(temp1.getmPollOption2()+"      "+percentage+"%");
		}
		
		if(getUsersVotedforOption3!=null&& getUsersVotedforOption3.length>0)
		{
			double percentage = (getUsersVotedforOption3.length*100)/totalUsersVoted;
			percentage = Math.round(percentage);
			temp1.setmPollOption3(temp1.getmPollOption3()+"      "+percentage+"%");
		}
		
		if(getUsersVotedforOption4!=null&& getUsersVotedforOption4.length>0)
		{
			double percentage = (getUsersVotedforOption4.length*100)/totalUsersVoted;
			percentage = Math.round(percentage);
			temp1.setmPollOption4(temp1.getmPollOption4()+"      "+percentage+"%");
		}
		
		if(position1==1){
			temp1.setmPollOption1UpvotedFlatOwner(temp1.getmPollOption1UpvotedFlatOwner()+"#?#"+SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences());
		}else if(position1==2){
			temp1.setmPollOption2UpvotedFlatOwner(temp1.getmPollOption2UpvotedFlatOwner()+"#?#"+SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences());
		}else if(position1==3){
			temp1.setmPollOption3UpvotedFlatOwner(temp1.getmPollOption3UpvotedFlatOwner()+"#?#"+SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences());
		}else if(position1==4){
			temp1.setmPollOption4UpvotedFlatOwner(temp1.getmPollOption4UpvotedFlatOwner()+"#?#"+SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences());
		}
	}	
}
