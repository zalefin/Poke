package com.example.pokeapp;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

class PokeSyncAdapter extends AbstractThreadedSyncAdapter {

    private ContentResolver contentResolver;

    public PokeSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        this.contentResolver = context.getContentResolver();
    }

    public PokeSyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        contentResolver = context.getContentResolver();
            }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

    }
}
