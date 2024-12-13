package br.edu.ifsp.dmo.pedratesouraepapel.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.dmo.pedratesouraepapel.R
import br.edu.ifsp.dmo.pedratesouraepapel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var activateBot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configToolBar()
        configSpinner()
        configListener()
    }

    private fun configListener() {
        binding.buttonStart.setOnClickListener { startGame() }
        binding.battleBot.setOnCheckedChangeListener{ _, isChecked ->
            binding.edittextPlayer2.visibility = if (isChecked) View.GONE else View.VISIBLE
            activateBot = isChecked
        }
    }

    private fun configSpinner() {
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.tipos_jogos)
        )
        binding.spinnerButtles.adapter = adapter
    }
    private fun configToolBar() {
        supportActionBar?.hide()
    }
    private fun startGame() {
        val battles: Int = when (binding.spinnerButtles.selectedItemPosition) {
            0 -> 1
            1 -> 3
            else -> 5
        }

        if(validateName()){
            val mIntent = Intent(this, WarActivity::class.java)
            mIntent.putExtra(Constants.KEY_PLAYER_1, binding.edittextPlayer1.text.toString())
            mIntent.putExtra(Constants.KEY_PLAYER_2, binding.edittextPlayer2.text.toString())
            mIntent.putExtra(Constants.KEY_ROUNDS, battles)
            mIntent.putExtra(Constants.KEY_ACTIVATE_BOT, activateBot)
            startActivity(mIntent)
        }
    }

    private fun validateName(): Boolean{
        if(!activateBot){
            if(binding.edittextPlayer1.text.toString().isNotBlank() && binding.edittextPlayer2.text.toString().isNotBlank()){
                return true
            }
        }else if(binding.edittextPlayer1.text.toString().isNotBlank()){
            return true
        }

        Toast.makeText(this,
            "Preencha os Campos de Nome",
            Toast.LENGTH_LONG
        ).show()

        return false
    }
}