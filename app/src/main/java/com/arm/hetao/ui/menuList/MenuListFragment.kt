package com.arm.hetao.ui.menuList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.arm.hetao.databinding.FragmentMenuBinding
import com.arm.hetao.ui.base.BaseFragment
import com.arm.hetao.utils.LunarCalendar
import com.google.android.material.snackbar.Snackbar
import com.lxj.xpopup.XPopup
import com.sy007.calendar.CalendarConfig
import com.sy007.calendar.MonthViewBinder
import com.sy007.calendar.OnSelectedListener
import com.sy007.calendar.ScrollMode
import com.sy007.calendar.entity.CalendarDay
import com.sy007.calendar.formatDate
import java.util.Calendar

/**
 *    author : heyueyang
 *    time   : 2023/07/25
 *    desc   :
 *    version: 1.0
 */
class MenuListFragment : BaseFragment() {

    companion object {
        fun getInstance() = MenuListFragment()
    }

    private lateinit var binding: FragmentMenuBinding
    private lateinit var model: MenuListModel
    private var mSelectedDay: CalendarDay? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCalendar()
        model = ViewModelProvider(this)[MenuListModel::class.java]
        model.data.observe(viewLifecycleOwner) {
            dismissLoading()
            if (it.isNullOrEmpty()) {
                Snackbar.make(
                    binding.cvSingleCalendarView,
                    "当天没有饮食信息",
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                XPopup.Builder(requireContext())
                    .asCustom(
                        PopMenuList(
                            requireContext(),
                            mSelectedDay?.formatDate("yyyy年MM月dd日") ?: "",
                            data = it
                        )
                    )
                    .show()
            }
        }
    }

    private fun initCalendar() {
        LunarCalendar.init(requireContext())
        binding.cvSingleCalendarView.apply {
            val startCalendar = Calendar.getInstance().apply {
                add(Calendar.YEAR, -1)
                set(Calendar.DAY_OF_MONTH, getActualMinimum(Calendar.DAY_OF_MONTH))
            }
            val endCalendar = Calendar.getInstance()
            val calendarConfig = CalendarConfig(startCalendar, endCalendar).apply {
                orientation = RecyclerView.VERTICAL
                scrollMode = ScrollMode.CONTINUITIES
                isDisplayExtraDay = false
            }
            monthViewBinder =
                object : MonthViewBinder<SingleMonthViewSimple2> {
                    override fun create(parent: ViewGroup): SingleMonthViewSimple2 {
                        return SingleMonthViewSimple2(requireContext())
                    }

                    override fun onBind(view: SingleMonthViewSimple2, calendarDay: CalendarDay) {
                        view.apply {
                            onSelectedListener = object : OnSelectedListener {
                                override fun onSelected(selected: CalendarDay) {
                                    showLoading()
                                    mSelectedDay = selected
                                    smoothScrollToMonth(selected)
                                    model.getDataByDate(selected.formatDate("yyyy-MM-dd"))
                                }
                            }
                        }
                    }

                }
            setUp(calendarConfig)
            scrollToDay(CalendarDay(endCalendar))
        }
    }


}