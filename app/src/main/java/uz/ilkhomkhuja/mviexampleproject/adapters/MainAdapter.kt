package uz.ilkhomkhuja.mviexampleproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.ilkhomkhuja.mviexampleproject.databinding.ItemLayoutBinding
import uz.ilkhomkhuja.mviexampleproject.models.User

class MainAdapter(
    private val users: ArrayList<User>
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(private var itemLayoutBinding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(itemLayoutBinding.root) {

        fun onBind(user: User) {
            itemLayoutBinding.textViewUserName.text = user.name
            itemLayoutBinding.textViewUserEmail.text = user.email
            Picasso.get()
                .load(user.avatar)
                .into(itemLayoutBinding.imageViewAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.onBind(users[position])

    fun addData(list: List<User>) {
        users.addAll(list)
    }
}