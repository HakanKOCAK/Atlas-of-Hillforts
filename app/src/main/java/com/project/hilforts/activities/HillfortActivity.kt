package com.project.hilforts.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.project.hilforts.R
import com.project.hilforts.helpers.readImage
import com.project.hilforts.helpers.readImageFromPath
import com.project.hilforts.helpers.showImagePicker
import com.project.hilforts.main.MainApp
import com.project.hilforts.models.HillfortModel
import kotlinx.android.synthetic.main.activity_hillforts.*
import kotlinx.android.synthetic.main.activity_hillforts.description
import kotlinx.android.synthetic.main.activity_hillforts.hillfortTitle
import kotlinx.android.synthetic.main.card_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast

class HillfortActivity : AppCompatActivity(), AnkoLogger {

    var hillfort = HillfortModel()
    lateinit var app: MainApp
    val IMAGE_REQUEST1 = 1
    val IMAGE_REQUEST2 = 2
    val IMAGE_REQUEST3 = 3
    val IMAGE_REQUEST4 = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillforts)
        app = application as MainApp

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        var edit = false

        if(intent.hasExtra("hillfort_edit")){
            edit = true
            hillfort = intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            hillfortTitle.setText(hillfort.title)
            description.setText(hillfort.description)

            if(hillfort.image1 != ""){hillfortImage1.setImageBitmap(readImageFromPath(this, hillfort.image1))}
            if(hillfort.image2 != ""){hillfortImage2.setImageBitmap(readImageFromPath(this, hillfort.image2))}
            if(hillfort.image3 != ""){hillfortImage3.setImageBitmap(readImageFromPath(this, hillfort.image3))}
            if(hillfort.image4 != ""){hillfortImage4.setImageBitmap(readImageFromPath(this, hillfort.image4))}

            btnAdd.setText(R.string.save_hillfort)
        }

        hillfortImage1.setOnClickListener(){
            showImagePicker(this, IMAGE_REQUEST1)
        }

        hillfortImage2.setOnClickListener(){
            showImagePicker(this, IMAGE_REQUEST2)
        }

        hillfortImage3.setOnClickListener(){
            showImagePicker(this, IMAGE_REQUEST3)
        }

        hillfortImage4.setOnClickListener(){
            showImagePicker(this, IMAGE_REQUEST4)
        }

        btnAdd.setOnClickListener() {
            hillfort.title = hillfortTitle.text.toString()
            hillfort.description = description.text.toString()
            if(hillfort.title.isEmpty()){
                toast(R.string.enter_hillfort_title)
            } else {

                if(edit){
                    app.hillforts.update(hillfort.copy())
                } else {
                    app.hillforts.create(hillfort.copy())
                }
            }
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST1 -> {
                if (data != null) {
                    hillfort.image1 = data.getData().toString()
                    hillfortImage1.setImageBitmap(readImage(this, resultCode, data))
                }
            }
            IMAGE_REQUEST2 -> {
                if (data != null) {
                    hillfort.image2 = data.getData().toString()
                    hillfortImage2.setImageBitmap(readImage(this, resultCode, data))
                }
            }
            IMAGE_REQUEST3 -> {
                if (data != null) {
                    hillfort.image3 = data.getData().toString()
                    hillfortImage3.setImageBitmap(readImage(this, resultCode, data))
                }
            }
            IMAGE_REQUEST4 -> {
                if (data != null) {
                    hillfort.image4 = data.getData().toString()
                    hillfortImage4.setImageBitmap(readImage(this, resultCode, data))
                }
            }
        }
    }
}
