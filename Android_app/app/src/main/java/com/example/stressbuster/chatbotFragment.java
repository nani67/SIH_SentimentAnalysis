package com.example.stressbuster;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ai.api.AIDataService;
import ai.api.AIListener;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;

public class chatbotFragment extends Fragment implements AIListener {

    private OnFragmentInteractionListener mListener;

    public chatbotFragment() {
        // Required empty public constructor
    }

    public static chatbotFragment newInstance() {
        return new chatbotFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final AIConfiguration config = new AIConfiguration("68fd4b7aa23f4c07b9270246d9dc1e0d",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiService = AIService.getService(getContext(), config);
        aiDataService = new AIDataService(config);
        aiRequest = new AIRequest();

        aiService.setListener(this);

    }

    private AIService aiService;
    private AIDataService aiDataService;
    private AIRequest aiRequest;


    LinearLayout layout;
    ScrollView scrollView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chatbot, container, false);
        final FloatingActionButton aiButton = view.findViewById(R.id.sendMessageToChatbot);
        scrollView = view.findViewById(R.id.scrollView);
        layout = view.findViewById(R.id.chatbotForChatbotFragment);

        aiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aiService.startListening();
            }
        });

        FloatingActionButton sendTextMsg = view.findViewById(R.id.sendTextMessageToChatbot);

        final EditText editText = view.findViewById(R.id.editTextForChatbot);

        sendTextMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = editText.getText().toString();

                addMessageBox("You\n" + query, 1);

                aiRequest.setQuery(query);

                new AsyncTask<AIRequest, Void, AIResponse>() {

                    @Override
                    protected AIResponse doInBackground(AIRequest... requests) {
                        final AIRequest request = requests[0];
                        try {
                            final AIResponse response = aiDataService.request(aiRequest);
                            return response;
                        } catch (AIServiceException e) {
                        }
                        return null;
                    }
                    @Override
                    protected void onPostExecute(AIResponse aiResponse) {
                        if (aiResponse != null) {
                            final String responseQuery = aiResponse.getResult().getFulfillment().getSpeech();
                            addMessageBox("Bot" + "\n" + responseQuery, 2);
                            Log.d("Resolved Query", responseQuery);
                        }
                    }

                }.execute(aiRequest);

            }
        });

        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResult(AIResponse result) {
        Result resultForDisplay = result.getResult();
        String queryOnes = resultForDisplay.getResolvedQuery();
        addMessageBox("You\n" + queryOnes, 1);

        addMessageBox("Bot" + "\n" + resultForDisplay.getFulfillment().getSpeech(), 2);
        Log.d("Query using Speech" , resultForDisplay.getFulfillment().getSpeech());

    }

















    public void addMessageBox(String message, int type) {
        TextView textView = new TextView(getContext());
        textView.setText(message);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 10);
        textView.setLayoutParams(lp);

        if (type == 1) {
            textView.setBackgroundResource(R.drawable.rounded_corner1);
            textView.setTextColor(Color.parseColor("#000000"));
            textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            textView.setTextSize(16);
        } else {
            textView.setBackgroundResource(R.drawable.rounded_corner2);
            textView.setTextColor(Color.parseColor("#000000"));
            textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            textView.setTextSize(16);
        }

        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }







    @Override
    public void onError(AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {


    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
