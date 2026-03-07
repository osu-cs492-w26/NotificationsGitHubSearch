package edu.oregonstate.cs492.notificationsgithubsearch.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import edu.oregonstate.cs492.notificationsgithubsearch.R

class GitHubRepoDetailFragment : Fragment(R.layout.fragment_github_repo_detail) {
    private val args: GitHubRepoDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repoNameTV: TextView = view.findViewById(R.id.tv_repo_name)
        val repoStarsTV: TextView = view.findViewById(R.id.tv_repo_stars)
        val repoDescriptionTV: TextView = view.findViewById(R.id.tv_repo_description)

        repoNameTV.text = args.repo.name
        repoStarsTV.text = args.repo.stars.toString()
        repoDescriptionTV.text = args.repo.description
    }
}