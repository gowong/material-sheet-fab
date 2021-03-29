package com.gordonwong.materialsheetfab.sample.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gordonwong.materialsheetfab.sample.R;
import com.gordonwong.materialsheetfab.sample.models.Note;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Gordon Wong on 7/18/2015.
 *
 * Adapter for the all items screen.
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

	private final Note[] notes;

	public NotesAdapter(Context context, int numNotes) {
		notes = generateNotes(context, numNotes);
	}

	@NonNull
	@Override
	public NotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_note, parent,
				false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Note noteModel = notes[position];
		String title = noteModel.getTitle();
		String note = noteModel.getNote();
		String info = noteModel.getInfo();
		int infoImage = noteModel.getInfoImage();
		int color = noteModel.getColor();

		// Set text
		holder.titleTextView.setText(title);
		holder.noteTextView.setText(note);
		holder.infoTextView.setText(info);

		// Set image
		if (infoImage != 0) {
			holder.infoImageView.setImageResource(infoImage);
		}

		// Set visibilities
		holder.titleTextView.setVisibility(TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);
		holder.noteTextView.setVisibility(TextUtils.isEmpty(note) ? View.GONE : View.VISIBLE);
		holder.infoLayout.setVisibility(TextUtils.isEmpty(info) ? View.GONE : View.VISIBLE);

		// Set padding
		int paddingTop = (holder.titleTextView.getVisibility() != View.VISIBLE) ? 0
				: holder.itemView.getContext().getResources()
						.getDimensionPixelSize(R.dimen.note_content_spacing);
		holder.noteTextView.setPadding(holder.noteTextView.getPaddingLeft(), paddingTop,
				holder.noteTextView.getPaddingRight(), holder.noteTextView.getPaddingBottom());

		// Set background color
		((CardView) holder.itemView).setCardBackgroundColor(color);
	}

	@Override
	public int getItemCount() {
		return notes.length;
	}

	private Note[] generateNotes(Context context, int numNotes) {
		Note[] notes = new Note[numNotes];
		for (int i = 0; i < notes.length; i++) {
			notes[i] = Note.randomNote(context);
		}
		return notes;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public final TextView titleTextView;
		public final TextView noteTextView;
		public final LinearLayout infoLayout;
		public final TextView infoTextView;
		public final ImageView infoImageView;

		public ViewHolder(View itemView) {
			super(itemView);
			titleTextView = itemView.findViewById(R.id.note_title);
			noteTextView = itemView.findViewById(R.id.note_text);
			infoLayout = itemView.findViewById(R.id.note_info_layout);
			infoTextView = itemView.findViewById(R.id.note_info);
			infoImageView = itemView.findViewById(R.id.note_info_image);
		}
	}

}
