package com.example.bms;

import com.example.bms.dummy.DummyContent;
import com.example.bms.dummy.DummyContent.DummyItem;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

/**
 * An activity representing a list of BridgeInfo. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link BridgeInfoDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link BridgeInfoListFragment} and the item details (if present) is a
 * {@link BridgeInfoDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link BridgeInfoListFragment.Callbacks} interface to listen for item
 * selections.
 */
public class BridgeInfoListActivity extends BaseActivity implements
		BridgeInfoListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	private boolean isBridge;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bridgeinfo_list);
		if (findViewById(R.id.bridgeinfo_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;
			DummyContent.ITEMS.clear();
			if(getIntent().getStringExtra("bridgeId")!=null){
				isBridge = true;
				DummyContent.addItem(new DummyItem("11", "识别"));
				DummyContent.addItem(new DummyItem("12", "结构"));
				DummyContent.addItem(new DummyItem("13", "经济"));
				DummyContent.addItem(new DummyItem("14", "档案"));
				DummyContent.addItem(new DummyItem("15", "责任人"));
				DummyContent.addItem(new DummyItem("16", "资料"));
				DummyContent.addItem(new DummyItem("17", "其他"));
			}else{
				isBridge = false;
				DummyContent.addItem(new DummyItem("21", "识别"));
				DummyContent.addItem(new DummyItem("22", "病害"));
				DummyContent.addItem(new DummyItem("23", "评价"));
			}
			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((BridgeInfoListFragment) getFragmentManager().findFragmentById(
					R.id.bridgeinfo_list)).setActivateOnItemClick(true);
			
			if(isBridge){
				onItemSelected("11");
			}else{
				onItemSelected("21");
			}
			
		}

		// TODO: If exposing deep links into your app, handle intents here.
	}

	/**
	 * Callback method from {@link BridgeInfoListFragment.Callbacks} indicating
	 * that the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(BridgeInfoDetailFragment.ARG_ITEM_ID, id);
			if(isBridge){
				arguments.putString("bridgeId", getIntent().getStringExtra("bridgeId"));
				setTitle(getIntent().getStringExtra("bridgeName"));
			}else{
				arguments.putString("culvertId", getIntent().getStringExtra("culvertId"));
				setTitle(getIntent().getStringExtra("culvertName"));
			}
			
			BridgeInfoDetailFragment fragment = new BridgeInfoDetailFragment();
			fragment.setArguments(arguments);
			getFragmentManager().beginTransaction()
					.replace(R.id.bridgeinfo_detail_container, fragment)
					.commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this,
					BridgeInfoDetailActivity.class);
			detailIntent.putExtra(BridgeInfoDetailFragment.ARG_ITEM_ID, id);
			detailIntent.putExtra("bridgeId", getIntent().getStringExtra("bridgeId"));
			startActivity(detailIntent);
		}
	}
}
