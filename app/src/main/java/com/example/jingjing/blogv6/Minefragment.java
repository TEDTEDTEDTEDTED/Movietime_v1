package com.example.jingjing.blogv6;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Minefragment extends Fragment  implements AdapterView.OnItemClickListener {
    private GridView gridView;
    private String[] names = {"我的清單", "我的錢包", "我的優惠", "帳戶管理", "客服中心", "其他設定"};
    ImageView imgacount;
    int[] icons = {R.drawable.list, R.drawable.money, R.drawable.favor, R.drawable.administer,
            R.drawable.customer, R.drawable.setting};

    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mine, null);
    }
    @Override

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = (GridView) getView().findViewById(R.id.grid);
        IconAdapter adapter =new IconAdapter();
        gridView.setAdapter(adapter);
        names[0]="我的清單";
        names[1]="我的錢包";
        names[2]="我的優惠";
        names[3]="帳戶管理";
        names[4]="客服中心";
        names[5]="其他設定";
        imgacount = (ImageView) getView().findViewById(R.id.name);
        imgacount.setOnClickListener(new ViewClickListener());
        gridView.setOnItemClickListener(this);




    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        switch ((int)id){
            case R.drawable.list:
                Toast.makeText(view.getContext(), "我的清單", Toast.LENGTH_LONG).show();
                intent = new Intent(this.getActivity(),MyMovieListActivity.class);
                //mediaStreamIntent.putExtra("sermon_details", (android.os.Parcelable) mDataset.get(position));
                this.getActivity().startActivity(intent);
                break;
            case R.drawable.money:
                Toast.makeText(view.getContext(), "我的錢包", Toast.LENGTH_LONG).show();
                intent = new Intent(this.getActivity(),MyWalletActivity.class);
                //mediaStreamIntent.putExtra("sermon_details", (android.os.Parcelable) mDataset.get(position));
                this.getActivity().startActivity(intent);
                break;
            case R.drawable.favor:
                Toast.makeText(view.getContext(), "我的優惠", Toast.LENGTH_LONG).show();
                intent = new Intent(this.getActivity(),MyWalletActivity.class);
                //mediaStreamIntent.putExtra("sermon_details", (android.os.Parcelable) mDataset.get(position));
                this.getActivity().startActivity(intent);
                break;
            case R.drawable.administer:
                Toast.makeText(view.getContext(), "帳戶管理", Toast.LENGTH_LONG).show();
                intent = new Intent(this.getActivity(),AcountManagementActivity.class);
                //mediaStreamIntent.putExtra("sermon_details", (android.os.Parcelable) mDataset.get(position));
                this.getActivity().startActivity(intent);
                break;

            case R.drawable.customer:
                Toast.makeText(view.getContext(), "客服中心", Toast.LENGTH_LONG).show();
                intent = new Intent(this.getActivity(),ServiceActivity.class);
                //mediaStreamIntent.putExtra("sermon_details", (android.os.Parcelable) mDataset.get(position));
                this.getActivity().startActivity(intent);
                break;

            case R.drawable.setting:
                Toast.makeText(view.getContext(), "其他設定", Toast.LENGTH_LONG).show();
                intent = new Intent(this.getActivity(),SettingActivity.class);
                //mediaStreamIntent.putExtra("sermon_details", (android.os.Parcelable) mDataset.get(position));
                this.getActivity().startActivity(intent);
                break;
        }
    }


    class IconAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return names[position];
        }

        @Override
        public long getItemId(int position) {
            return icons[position];
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                row = getLayoutInflater().inflate(R.layout.mine_row, null);
                ImageView image = (ImageView) row.findViewById(R.id.item_image);
                TextView text = (TextView) row.findViewById(R.id.item_text);
                image.setImageResource(icons[position]);
                text.setText(names[position]);
            }
            return row;
        }
    }



    class ViewClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "我的帳戶", Toast.LENGTH_LONG).show();
        }
    }


}

