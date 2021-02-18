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

        insertDataClientInFields("Client Test", "Address Test", "99999999999")
        insertDataClientInFields("Amanda Alves", "Address Test", "99999999999")
    }

    private fun insertDataClientInFields(name: String, address: String, tel: String) {
        clickFabNew(R.id.new_client)
        insertDataInFieldWithParent(
            R.id.client_form_name,
            R.id.clientNameTextInputLayout,
            name
        )
        insertDataInFieldWithParent(
            R.id.client_form_address,
            R.id.clientAddressTextInputLayout,
            address
        )
        insertDataInFieldWithParent(
            R.id.client_form_phone,
            R.id.clientTelTextInputLayout,
            tel
        )
        clickSaveDialog()
    }

    @Test
    fun insertServiceFlow() {
        clickBottomNavigation(R.id.listServiceFragment, SERVICES)

        insertDataServiceInFields("Service Test", "20.50")
        insertDataServiceInFields("Aula de Ingles", "25")

    }

    private fun insertDataServiceInFields(description: String, value: String) {
        clickFabNew(R.id.new_service)

        insertDataInFieldWithParent(
            R.id.service_form_description,
            R.id.serviceDescriptionTextInputLayout,
            description
        )

        insertDataInFieldWithParent(
            R.id.service_form_value,
            R.id.serviceValueTextInputLayout,
            value
        )
        clickSaveDialog()
    }
}
