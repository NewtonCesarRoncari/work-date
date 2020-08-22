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
        insertDataClientInFields("Thiago Souza", "Address Test", "99999999999")
        insertDataClientInFields("Lucio Henrique", "Address Test", "99999999999")
        insertDataClientInFields("Matheus Gontijo", "Address Test", "99999999999")
        insertDataClientInFields("Matheus", "Address Test", "99999999999")
        insertDataClientInFields("Marcos", "Address Test", "99999999999")
        insertDataClientInFields("Tarzan", "Address Test", "99999999999")
        insertDataClientInFields("Marcos Vinicius", "Address Test", "99999999999")
        insertDataClientInFields("Marcos Apolinario", "Address Test", "99999999999")
        insertDataClientInFields("Smeagol", "Address Test", "99999999999")
        insertDataClientInFields("Gandalf O Cinzento", "Address Test", "99999999999")
        insertDataClientInFields("Bilbo Bolseiro", "Address Test", "99999999999")
        insertDataClientInFields("Harry Potter", "Address Test", "99999999999")
        insertDataClientInFields("Alisson Alves", "Address Test", "99999999999")
        insertDataClientInFields("Guilherme Alves", "Address Test", "99999999999")
        insertDataClientInFields("Luiz Andrade", "Address Test", "99999999999")
        insertDataClientInFields("Clark Kent", "Address Test", "99999999999")
        insertDataClientInFields("Adam West", "Address Test", "99999999999")
        insertDataClientInFields("Bruce Wayne", "Address Test", "99999999999")
        insertDataClientInFields("Bruce Banner", "Address Test", "99999999999")
        insertDataClientInFields("Ronaldinho Gaucho", "Address Test", "99999999999")
        insertDataClientInFields("Arlindo Cruz", "Address Test", "99999999999")
        insertDataClientInFields("Roberto Assuncao", "Address Test", "99999999999")
        insertDataClientInFields("Optimus Prime", "Address Test", "99999999999")
        insertDataClientInFields("Rodrigo Santoro", "Address Test", "99999999999")
        insertDataClientInFields("Celima Alves", "Address Test", "99999999999")
        insertDataClientInFields("Nilton Roncari", "Address Test", "99999999999")
        insertDataClientInFields("Ranny Sousa", "Address Test", "99999999999")
        insertDataClientInFields("Sam Winchester", "Address Test", "99999999999")
        insertDataClientInFields("Dean Winchester", "Address Test", "99999999999")
        insertDataClientInFields("Bob Singer", "Address Test", "99999999999")
        insertDataClientInFields("Martha Wayne", "Address Test", "99999999999")
        insertDataClientInFields("Norman Osborn", "Address Test", "99999999999")
        insertDataClientInFields("Peter Parker", "Address Test", "99999999999")
        insertDataClientInFields("Diana", "Address Test", "99999999999")
        insertDataClientInFields("Suzane Von Richthofen", "Address Test", "99999999999")
        insertDataClientInFields("Amelie Poulain", "Address Test", "99999999999")
        insertDataClientInFields("Maria Silva", "Address Test", "99999999999")
        insertDataClientInFields("Dilma Rousseff", "Address Test", "99999999999")
        insertDataClientInFields("Carol Moreira", "Address Test", "99999999999")
        insertDataClientInFields("Louis Lane", "Address Test", "99999999999")

    }

    private fun insertDataClientInFields(name: String, address: String, tel: String) {
        clickFabNew(R.id.new_client)
        insertDataInFieldWithParent(
            R.id.client_formulary_name,
            R.id.clientNameTextInputLayout,
            name
        )
        insertDataInFieldWithParent(
            R.id.client_form_address,
            R.id.clientEnderecoTextInputLayout,
            address
        )
        insertDataInFieldWithParent(
            R.id.client_formulary_phone,
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
        insertDataServiceInFields("Aula de Judo", "150")
        insertDataServiceInFields("Assassinato Homologado", "320.20")
        insertDataServiceInFields("Luta Livre", "200")
        insertDataServiceInFields("Programa", "20.15")
        insertDataServiceInFields("Corrida", "8.50")
        insertDataServiceInFields("Malhacao", "10")
        insertDataServiceInFields("Academia", "15")
        insertDataServiceInFields("Aeropilotagem", "25")
        insertDataServiceInFields("Viagem Interdimensional", "90")
        insertDataServiceInFields("Massagem", "99.90")
        insertDataServiceInFields("Unha", "29")
        insertDataServiceInFields("Pe", "20.50")
        insertDataServiceInFields("Esfoliacao", "20.50")
        insertDataServiceInFields("Jogar Videogame", "35")
        insertDataServiceInFields("Pintura Capilar", "49.90")
        insertDataServiceInFields("Corte de Cabelo", "49.90")
        insertDataServiceInFields("Corte de Cabelo Gourmet", "50")
        insertDataServiceInFields("Ensaio Musical", "49.90")
        insertDataServiceInFields("Curso de Xadrez", "120")
        insertDataServiceInFields("TCC", "600")
        insertDataServiceInFields("Projeto Manufaturado", "45")
        insertDataServiceInFields("Horario de Futebol", "100")

    }

    private fun insertDataServiceInFields(description: String, value: String) {
        clickFabNew(R.id.new_service)

        insertDataInFieldWithParent(
            R.id.service_formulary_description,
            R.id.serviceDescriptionTextInputLayout,
            description
        )

        insertDataInFieldWithParent(
            R.id.service_formulary_value,
            R.id.serviceValueTextInputLayout,
            value
        )
        clickSaveDialog()
    }
}
