package com.bagashyt.juaraandroid

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.bagashyt.juaraandroid.databinding.FragmentAddNoteBinding
import com.bagashyt.juaraandroid.note.Note
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class AddNoteFragment : Fragment() {
    private val viewModel: NoteViewModel by activityViewModels {
        NoteViewModelFactory(
            (activity?.application as NoteApplication).database
                .NoteDao()
        )
    }
    private val navigationArgs: NoteDetailFragmentArgs by navArgs()
    lateinit var note: Note

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun isEntryValid(): Boolean {
        val title = binding.ETTitle.text.toString().isNullOrEmpty()
        val note = binding.ETNote.text.toString().isNullOrBlank()
        return !title && !note
    }

    private fun bind(note: Note){
        binding.apply {
            ETTitle.setText(note.noteTitle, TextView.BufferType.SPANNABLE)
            ETNote.setText(note.noteText, TextView.BufferType.SPANNABLE)
            saveButton.setOnClickListener {
                Log.d("deubug", "BUTON PRESSED")
                updateNote() }
        }
    }

    private fun addNewNote(){
        if(isEntryValid()){
            viewModel.addNewNote(
                binding.ETTitle.text.toString(),
                binding.ETNote.text.toString()
            )
        val action = AddNoteFragmentDirections.actionAddNoteFragmentToNoteListFragment()
            findNavController().navigate(action)
        }
    }

    private fun updateNote(){
        if (isEntryValid()){
            viewModel.updateNote(
                this.navigationArgs.noteId,
                this.binding.ETTitle.text.toString(),
                this.binding.ETNote.text.toString()
            )
            val action = AddNoteFragmentDirections.actionAddNoteFragmentToNoteListFragment()
            findNavController().navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.noteId
        if (id > 0){
            viewModel.retrieveNote(id).observe(this.viewLifecycleOwner){ selectedItem ->
                note = selectedItem
                bind(note)
            }
        } else {
            binding.saveButton.setOnClickListener {
                Log.d("debug","SAVE PRESSED")
                addNewNote()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val inputMeManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMeManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}