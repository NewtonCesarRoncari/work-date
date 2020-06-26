package com.br.workdate.view.flow


import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.br.workdate.R
import com.br.workdate.view.NavigationActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class InsertModelsFlowTest : BaseFlowTest() {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(NavigationActivity::class.java)

    @Test
    fun insertClientFlow() {
        clickBottomNavigation(R.id.listClientFragment, CLIENTS)
        clickFabNew(R.id.new_client)

        insertDataInFieldWithParent(
            R.id.client_formulary_name,
            R.id.clientNameTextInputLayout,
            "Client Test"
        )
        insertDataInFieldWithParent(
            R.id.client_formulary_address,
            R.id.clientEnderecoTextInputLayout,
            "Address Test"
        )
        insertDataInFieldWithParent(
            R.id.client_formulary_phone,
            R.id.clientTelTextInputLayout,
            "99999999999"
        )

        clickSaveDialog()
    }

    @Test
    fun insertServiceFlow() {
        clickBottomNavigation(R.id.listServiceFragment, SERVICES)
        clickFabNew(R.id.new_service)

        insertDataInFieldWithParent(
            R.id.service_formulary_description,
            R.id.serviceDescriptionTextInputLayout,
            "Service Test"
        )

        insertDataInFieldWithParent(
            R.id.service_formulary_value,
            R.id.serviceValueTextInputLayout,
            "20.50"
        )

        clickSaveDialog()
    }
}
