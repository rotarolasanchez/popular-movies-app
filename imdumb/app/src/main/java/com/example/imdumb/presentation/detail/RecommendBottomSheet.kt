package com.example.imdumb.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.imdumb.databinding.BottomSheetRecommendBinding
import com.example.imdumb.domain.model.MovieModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RecommendBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetRecommendBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(movie: MovieModel): RecommendBottomSheet {
            val args = Bundle()
            args.putParcelable("movie", movie)
            val fragment = RecommendBottomSheet()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetRecommendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val movie = arguments?.getParcelable<MovieModel>("movie")
        binding.tvMovieTitle.text = movie?.title
        binding.tvMovieSummary.text = movie?.overview

        binding.btnConfirm.setOnClickListener {
            val comment = binding.etComment.text.toString()
            if (comment.isBlank()) {
                binding.etComment.error = "Por favor deja un comentario"
                return@setOnClickListener
            }
            Toast.makeText(context, "Recomendación enviada para: ${movie?.title}", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
