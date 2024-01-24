import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.kotlin3_3.databinding.FragmentOnBoardBinding
import com.example.kotlin3_3.ui.adapters.OnBoardAdapter
class OnBoardFragment : Fragment() {

    private var _binding: FragmentOnBoardBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        setupListeners()
    }
    private fun initialize() = with(binding) {
        noteViewPager.adapter = OnBoardAdapter(this@OnBoardFragment)
        wormDotsIndicator.attachTo(noteViewPager)
    }

    private fun setupListeners() = with(binding.noteViewPager) {
        registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tvSkip.isInvisible = currentItem == 2
            }
        })

        binding.tvSkip.setOnClickListener {
            if (currentItem < 2) {
                setCurrentItem(currentItem + 1, true)

            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}