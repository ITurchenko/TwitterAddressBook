package ru.caseagency.twitteraddressbook.twitter;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.github.gorbin.asne.core.persons.SocialPerson;
import com.github.gorbin.asne.twitter.TwitterPerson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.caseagency.twitteraddressbook.R;

class ContentAdapter extends BaseAdapter {
    private TwitterActivityFragment activityFragment;

    private List<SocialPerson> data = new ArrayList<SocialPerson>();

    public ContentAdapter(TwitterActivityFragment activityFragment) {
        this.activityFragment = activityFragment;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public SocialPerson getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        FragmentActivity context = activityFragment.getActivity();

        if (view == null) {
            view = View.inflate(context, R.layout.contact_list_item, null);
            view.setTag(ViewHolder.fromView(view));
        }

        SocialPerson person = getItem(i);
        ViewHolder holder = (ViewHolder) view.getTag();

        // If the user didn't do a search, or the search string didn't match a display
        // name, show the display name without highlighting
        holder.text1.setText(person.name);

        // If the search search is empty, hide the second line of text
        holder.text2.setVisibility(View.GONE);

        Picasso.with(context)
                .load(person.avatarURL)
                .placeholder(R.drawable.ic_contact_picture_holo_light)
                .tag(context)
                .into(holder.icon);

        return view;
    }

    public void add(SocialPerson person) {
        data.add(person);
        notifyDataSetChanged();
    }

    //--------------------------------------------------------------------------------------------

    private static class ViewHolder {
        TextView text1;
        TextView text2;
        QuickContactBadge icon;

        private ViewHolder() {
        }

        public static ViewHolder fromView(View view) {
            final ViewHolder holder = new ViewHolder();
            holder.text1 = (TextView) view.findViewById(android.R.id.text1);
            holder.text2 = (TextView) view.findViewById(android.R.id.text2);
            holder.icon = (QuickContactBadge) view.findViewById(android.R.id.icon);

            return holder;
        }
    }
}
