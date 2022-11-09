package com.example.starwars

import androidx.activity.OnBackPressedDispatcher
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import com.example.starwars.ui.CharacterDetailsFragment
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test


class MainActivityTest {

    lateinit var onBackPressedDispatcher: OnBackPressedDispatcher
    lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
       // navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        // Create a test specific OnBackPressedDispatcher,
        // giving you complete control over its behavior
        onBackPressedDispatcher = OnBackPressedDispatcher()

        // Here we use the launchInContainer method that
        // generates a FragmentFactory from a constructor,
        // automatically figuring out what class you want
//        fragmentScenario = launchFragmentInContainer {
//            CharacterDetailsFragment()
//        }
//        fragmentScenario.onFragment(object : FragmentScenario.FragmentAction<CharacterDetailsFragment> {
//            override fun perform(fragment: CharacterDetailsFragment) {
//                Navigation.setViewNavController(fragment.requireView(), navController)
//                navController.setGraph(R.navigation.nav_graph)
//                // Set the current destination to fragmentB
//                navController.setCurrentDestination(R.id.characterDetailsFragment)
//            }
//        })
    }

    @Test
    fun whenBackButtonClicked_NavigationGoesUp() {
        navController.navigateUp()

        assertTrue(onBackPressedDispatcher.hasEnabledCallbacks())

        onBackPressedDispatcher.onBackPressed()

        assertFalse(onBackPressedDispatcher.hasEnabledCallbacks())
    }
}
