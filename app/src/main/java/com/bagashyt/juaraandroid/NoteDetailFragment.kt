package com.bagashyt.juaraandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bagashyt.juaraandroid.databinding.FragmentNoteDetailBinding
import com.bagashyt.juaraandroid.note.Note
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NoteDetailFragment : Fragment(){
    private val navigatorArgs : NoteDetailFragmentArgs by navArgs()
    lateinit var note: Note

    private val viewModel : NoteViewModel by activityViewModels {
        NoteViewModelFactory(
            (activity?.application as NoteApplication).database.NoteDao()
        )
    }

    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun bind(note: Note){
        binding.apply {
            tvDetailNoteTitle.text = note.noteTitle
            tvDetailNote.text = note.noteText
            btnEdit.setOnClickListener { editNote()  }
            btnDelete.setOnClickListener { showConfirmationDialog() }
        }
    }

    private fun editNote(){
        val action = NoteDetailFragmentDirections.actionNoteDetailFragmentToAddNoteFragment(
            "Edit Note", note.id)
        this.findNavController().navigate(action)

    }

    private fun deleteNote(){
        viewModel.deleteNote(note)
        findNavController().navigateUp()
    }

    private fun showConfirmationDialog(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Attention")
            .setMessage("Are you sure want to delete?")
            .setCancelable(false)
            .setNegativeButton("No"){_, _ ->}
            .setPositiveButton("Yes"){_,_ ->
                deleteNote()
            }
            .show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigatorArgs.noteId
        viewModel.retrieveNote(id).observe(this.viewLifecycleOwner){selectedItem ->
            note = selectedItem
            bind(note)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}