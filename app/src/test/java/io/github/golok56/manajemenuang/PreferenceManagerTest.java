package io.github.golok56.manajemenuang;

import android.content.Context;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import io.github.golok56.manajemenuang.utility.PreferenceManager;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author Satria Adi Putra
 */
@RunWith(MockitoJUnitRunner.class)
public class PreferenceManagerTest {

    private static final String NAME = "Satria Adi";
    private static final String WALLET = "100000";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    Context context;

    PreferenceManager mPref;

    @Before
    public void init() {
        mPref = new PreferenceManager(context);
    }

    @Test
    public void getUsernameShouldReturnNullAtFirst() {
        // Default value of mPref#getUserName is null, so it should return null at first time
        assertEquals(null, mPref.getUserName());
    }

    @Test
    public void getUserNameShouldReturnName() {
        // Change a user name therefore mPref#getUserName should return NAME
        mPref.setUserName(NAME);
        assertEquals(NAME, mPref.getUserName());
    }

    @Test
    public void getWalletShouldReturnZeroAtFirst() {
        // Default value of mPref#getWallet is "0", so it should return 0 at first time
        assertEquals(0, mPref.getWallet(), 0);
    }

    @Test
    public void getWalletShouldReturnWallet() {
        // Change the wallet with a new value, therefore mPref#getWallet should return WALLET
        mPref.updateWallet(WALLET);
        when(mPref.getWallet()).thenReturn(Double.parseDouble(WALLET));
    }

}
