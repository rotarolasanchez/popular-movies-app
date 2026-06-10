package com.example.imdumb.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.imdumb.databinding.BottomSheetRecommendBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RecommendBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetRecommendBinding? = null
    private val binding get() = _binding!!

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
        
        binding.btnConfirm.setOnClickListener {
            val comment = binding.etComment.text.toString()
            Toast.makeText(context, "Recomendación enviada: $comment", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
