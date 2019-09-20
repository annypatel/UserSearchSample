package sample.search.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import sample.search.R
import sample.search.domain.User
import sample.search.domain.UserSearchResult
import kotterknife.bindView

/**
 * Adapter for the list of [UserSearchResult].
 */
class UserSearchAdapter(
    avatarFadeDuration: Int,
    avatarCornerRadius: Int
) : RecyclerView.Adapter<UserSearchAdapter.UserSearchViewHolder>() {

    private var users: List<User> = emptyList()
    private val crossFade = withCrossFade(avatarFadeDuration)
    private val roundedCorners = RequestOptions().transform(RoundedCorners(avatarCornerRadius))

    fun setUsers(users: Set<User>) {
        this.users = users.toList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserSearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_search, parent, false)
        return UserSearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserSearchViewHolder, position: Int) {
        val user = users[position]
        holder.txtDisplayName.text = user.displayName
        holder.txtUsername.text = user.username

        Glide.with(holder.imgAvatar)
            .asBitmap()
            .transition(crossFade)
            .apply(roundedCorners)
            .load(user.avatarUrl)
            .into(holder.imgAvatar)
    }

    class UserSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgAvatar: ImageView by bindView(R.id.imgAvatar)
        val txtDisplayName: TextView by bindView(R.id.txtDisplayName)
        val txtUsername: TextView by bindView(R.id.txtUsername)
    }
}