package com.example.giftyhaus.data



import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.giftyhaus.models.OrderModel
import com.example.giftyhaus.models.ProductModel
import com.example.giftyhaus.navigation.ROUTE_HOME
import com.example.giftyhaus.navigation.ROUTE_VIEW_ORDERS
import com.example.giftyhaus.network.ImgurService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


class ProductViewModel: ViewModel() {
    private val database = FirebaseDatabase.getInstance().reference.child("Products")

    private fun getImgurService(): ImgurService {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.imgur.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ImgurService::class.java)
    }
    private fun getFileFromUri(context: Context, uri: Uri):
            File? {
        return try {
            val inputStream = context.contentResolver
                .openInputStream(uri)
            val file = File.createTempFile("temp_image", ".jpg", context.cacheDir)
            file.outputStream().use { output ->
                inputStream?.copyTo(output)
            }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun uploadProductwithImage(
        uri: Uri,
        context: Context,
        name: String,
        price: String,
        description: String,
        category: String,
        navController: NavController
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val file = getFileFromUri(context, uri)
                if (file == null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Failed to process image", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }

                val reqFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("image", file.name, reqFile)

                val response = getImgurService().uploadImage(
                    body,
                    "Client-ID f02cceb24ce9cd9"
                )

                if (response.isSuccessful) {
                    val imageUrl = response.body()?.data?.link ?: ""

                    val productId = database.child(category).push().key ?: ""
                    val product = ProductModel(name, price, description, imageUrl, productId,category)

                    database.child(category).child(productId).setValue(product)
                        .addOnSuccessListener {
                            viewModelScope.launch {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Product saved successfully", Toast.LENGTH_SHORT).show()
                                    navController.navigate(ROUTE_HOME)
                                }
                            }
                        }
                        .addOnFailureListener {
                            viewModelScope.launch {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Failed to save product", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Upload error", Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Exception: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }



    val categorizedProducts = mutableStateOf<Map<String, List<ProductModel>>>(emptyMap())

    fun viewProducts(
        products: SnapshotStateList<ProductModel>,
        category: String? = null
    ) {
        val ref = FirebaseDatabase.getInstance().getReference("Products")

        val query = if (category.isNullOrEmpty()) {
            ref
        } else {
            ref.orderByChild("category").equalTo(category)
        }

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                products.clear()
                for (snap in snapshot.children) {
                    val product = snap.getValue(ProductModel::class.java)
                    if (product != null) {
                        products.add(product)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Database Error: ${error.message}")
            }
        })
    }




    fun updateProduct(
        context: Context,
        navController: NavController,
        items: String,
        totalAmount: String,
        date: String,
        status: String,
        productId: String
    ) {
        // Corrected the reference path
        val databaseReference = FirebaseDatabase.getInstance()
            .getReference("Orders/$productId")

        // Create the updated product model
        val updatedOrder = OrderModel(items, totalAmount, date, status, productId)

        // Update the database
        databaseReference.setValue(updatedOrder)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Order Updated Successfully", Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_VIEW_ORDERS)
                } else {
                    Toast.makeText(context, "Order update failed", Toast.LENGTH_LONG).show()
                }
            }
    }
    fun deleteOrder(context: Context,orderId: String,
                    navController: NavController){
        AlertDialog.Builder(context)
            .setTitle("Delete Student")
            .setMessage("Are you sure you want to delete this student?")
            .setPositiveButton("Yes"){ _, _ ->

                val databaseReference = FirebaseDatabase.getInstance()
                    .getReference("Orders/$orderId")
                databaseReference.removeValue().addOnCompleteListener {
                        task ->
                    if (task.isSuccessful){

                        Toast.makeText(context,"Student deleted Successfully",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(context,"Student not deleted",Toast.LENGTH_LONG).show()
                    }
                }
            }
            .setNegativeButton("No"){ dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}