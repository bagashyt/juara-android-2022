package com.bagashyt.juaraandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagashyt.juaraandroid.databinding.FragmentNoteListBinding

class NoteListFragment : Fragment(){
    private val viewModel: NoteViewModel by activityViewModels {
        NoteViewModelFactory(
            (activity?.application as NoteApplication).database.NoteDao()
        )
    }

    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NoteListAdapter{
            val action = NoteListFragmentDirections.actionNoteListFragmentToNoteDetailFragment(it.id)
            this.findNavController().navigate(action)
        }
        binding.rvNotes.layoutManager = LinearLayoutManager(this.context)
        binding.rvNotes.adapter = adapter

        viewModel.allNote.observe(this.viewLifecycleOwner){items ->
            items.let {
                adapter.submitList(it)
            }
        }

        binding.floatingActionButton.setOnClickListener {
            val action = NoteListFragmentDirections.actionListFragmentToAddNoteFragment("Add Note")
            this.findNavController().navigate(action)
        }
    }
}