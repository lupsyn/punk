package app.punk.home

import app.punk.data.resultentities.EntryWithPaginatedBeers
import app.punk.utils.EntryFragment

/**
 * Shows "HomeFragment"
 */
class HomeFragment : EntryFragment<EntryWithPaginatedBeers, HomeViewModel>(HomeViewModel::class.java)