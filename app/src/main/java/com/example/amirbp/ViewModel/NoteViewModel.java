//package com.example.amirbp.ViewModel;
//
//import android.content.Context;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.ViewModel;
//
//import com.android.volley.VolleyError;
//import com.example.amirbp.Model.Contact;
//
//import java.util.List;
//
//import droidrocks.com.hishebionline.Api.NoteApiService;
//import droidrocks.com.hishebionline.Models.ApiResponse;
//import droidrocks.com.hishebionline.Models.NoteData;
//
//public class NoteViewModel extends ViewModel {
//    private final NoteApiService noteApiService;
//    private final MutableLiveData<List<Contact>> notesLiveData;
//
//    private final MutableLiveData<String> errorMessage;
//
//
//    private final MutableLiveData<ApiResponse> subscriptionExpired = new MutableLiveData<>();
//    private final MutableLiveData<ApiResponse> subscriptionPending = new MutableLiveData<>();
//
//
//    public NoteViewModel(Context context) {
//        noteApiService = new NoteApiService(context);
//        notesLiveData = new MutableLiveData<>();
//        isLoading = new MutableLiveData<>();
//        errorMessage = new MutableLiveData<>();
//        onFailedApiResponse = new MutableLiveData<>();
//        onUnAuthorizedApiResponse = new MutableLiveData<>();
//    }
//
//    public LiveData<List<NoteData>> getNotesLiveData() {
//        return notesLiveData;
//    }
//
//    public LiveData<Boolean> getIsLoading() {
//        return isLoading;
//    }
//
//    public LiveData<String> getErrorMessage() {
//        return errorMessage;
//    }
//
//    public MutableLiveData<ApiResponse> getOnFailedApiResponse() {
//        return onFailedApiResponse;
//    }
//
//    public MutableLiveData<ApiResponse> getOnUnAuthorizedApiResponse() {
//        return onUnAuthorizedApiResponse;
//    }
//
//    public MutableLiveData<ApiResponse> getSubscriptionExpired() {
//        return subscriptionExpired;
//    }
//
//    public MutableLiveData<ApiResponse> getSubscriptionPending() {
//        return subscriptionPending;
//    }
//
//    public void fetchNoteList() {
//        isLoading.setValue(true);
//        noteApiService.getNoteList(new NoteApiService.ResponseCallback<List<NoteData>>() {
//            @Override
//            public void onResponse(List<NoteData> response) {
//                isLoading.setValue(false);
//                notesLiveData.setValue(response);
//
//            }
//
//            @Override
//            public void onFailed(ApiResponse response) {
//                isLoading.setValue(false);
//                onFailedApiResponse.postValue(response);
//
//            }
//
//            @Override
//            public void onUnAuthorized(ApiResponse response) {
//                isLoading.setValue(false);
//                onUnAuthorizedApiResponse.setValue(response);
//            }
//
//            @Override
//            public void onError(VolleyError error) {
//                isLoading.setValue(false);
//                errorMessage.setValue(error.getMessage());
//
//            }
//
//            @Override
//            public void onSubscriptionExpired(ApiResponse response) {
//                isLoading.setValue(false);
//                subscriptionExpired.setValue(response);
//
//            }
//
//            @Override
//            public void onSubscriptionPending(ApiResponse response) {
//                isLoading.setValue(false);
//                subscriptionPending.setValue(response);
//            }
//        });
//    }
//
//    public void createNote(NoteData noteData) {
//        isLoading.setValue(true);
//        noteApiService.createNote(noteData, new NoteApiService.ResponseCallback<ApiResponse>() {
//            @Override
//            public void onResponse(ApiResponse response) {
//                isLoading.setValue(false);
//                fetchNoteList(); // Refresh the notes after creation
//
//            }
//
//            @Override
//            public void onFailed(ApiResponse response) {
//                onFailedApiResponse.setValue(response);
//            }
//
//            @Override
//            public void onUnAuthorized(ApiResponse response) {
//                isLoading.setValue(false);
//                onUnAuthorizedApiResponse.setValue(response);
//            }
//
//            @Override
//            public void onError(VolleyError error) {
//                errorMessage.setValue(error.getMessage());
//                isLoading.setValue(false);
//            }
//
//            @Override
//            public void onSubscriptionExpired(ApiResponse response) {
//                isLoading.setValue(false);
//                subscriptionExpired.setValue(response);
//
//            }
//
//            @Override
//            public void onSubscriptionPending(ApiResponse response) {
//                isLoading.setValue(false);
//                subscriptionPending.setValue(response);
//            }
//        });
//    }
//
//    public void updateNote(NoteData noteData) {
//        isLoading.setValue(true);
//        noteApiService.updateNote(noteData, new NoteApiService.ResponseCallback<ApiResponse>() {
//            @Override
//            public void onResponse(ApiResponse response) {
//                fetchNoteList(); // Refresh the notes after updating
//                isLoading.setValue(false);
//            }
//
//            @Override
//            public void onFailed(ApiResponse response) {
//                onFailedApiResponse.setValue(response);
//                isLoading.setValue(false);
//            }
//
//            @Override
//            public void onUnAuthorized(ApiResponse response) {
//                isLoading.setValue(false);
//                onUnAuthorizedApiResponse.setValue(response);
//
//            }
//
//            @Override
//            public void onError(VolleyError error) {
//                errorMessage.setValue(error.getMessage());
//                isLoading.setValue(false);
//            }
//
//            @Override
//            public void onSubscriptionExpired(ApiResponse response) {
//                isLoading.setValue(false);
//                subscriptionExpired.setValue(response);
//
//            }
//
//            @Override
//            public void onSubscriptionPending(ApiResponse response) {
//                isLoading.setValue(false);
//                subscriptionPending.setValue(response);
//            }
//        });
//    }
//
//    public void deleteNote(int noteId) {
//        isLoading.setValue(true);
//        noteApiService.deleteNote(noteId, new NoteApiService.ResponseCallback<ApiResponse>() {
//            @Override
//            public void onResponse(ApiResponse response) {
//                isLoading.setValue(false);
//                fetchNoteList(); // Refresh the notes after deletion
//
//            }
//
//            @Override
//            public void onFailed(ApiResponse response) {
//                isLoading.setValue(false);
//                onFailedApiResponse.setValue(response);
//            }
//
//            @Override
//            public void onUnAuthorized(ApiResponse response) {
//                isLoading.setValue(false);
//                onUnAuthorizedApiResponse.setValue(response);
//            }
//
//            @Override
//            public void onError(VolleyError error) {
//                isLoading.setValue(false);
//                errorMessage.setValue(error.getMessage());
//
//            }
//
//            @Override
//            public void onSubscriptionExpired(ApiResponse response) {
//                isLoading.setValue(false);
//                subscriptionExpired.setValue(response);
//
//            }
//
//            @Override
//            public void onSubscriptionPending(ApiResponse response) {
//                isLoading.setValue(false);
//                subscriptionPending.setValue(response);
//            }
//        });
//    }
//
//
//}
