package com.example.tabrizguilds.tabrizguilds.introPage;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.tabrizguilds.tabrizguilds.R;


/**
 * Created by mohamadHasan on 20/07/2017.
 */

public class IntroPageTransformer implements ViewPager.PageTransformer {



    @Override
    public void transformPage(View page, float position) {

        // Get the page index from the tag. This makes
        // it possible to know which page index you're
        // currently transforming - and that can be used
        // to make some important performance improvements.
        int pagePosition = (int) page.getTag();

        // Here you can do all kinds of stuff, like get the
        // width of the page and perform calculations based
        // on how far the user has swiped the page.
        int pageWidth = page.getWidth();
        float pageWidthTimesPosition = pageWidth * position;
        float absPosition = Math.abs(position);

        // Now it's time for the effects
        if (position <= -1.0f || position >= 1.0f) {

            // The page is not visible. This is a good place to stop
            // any potential work / animations you may have running.

        } else if (position == 0.0f) {

            // The page is selected. This is a good time to reset Views
            // after animations as you can't always count on the PageTransformer
            // callbacks to match up perfectly.

        } else {

            View txtIntro1 = page.findViewById(R.id.txtIntro1);
            View txtIntro1_1 = page.findViewById(R.id.txtIntro1_1);
            View imgIntro1 = page.findViewById(R.id.imgIntro1);
            View txtIntro2 = page.findViewById(R.id.txtIntro2);
            View txtIntro2_1 = page.findViewById(R.id.txtIntro2_1);
            View imgIntro2 = page.findViewById(R.id.imgIntro2);
            View txtIntro3 = page.findViewById(R.id.txtIntro3);
            View txtIntro3_1 = page.findViewById(R.id.txtIntro3_1);
            View imgIntro3 = page.findViewById(R.id.imgIntro3);
            View txtIntro4 = page.findViewById(R.id.txtIntro4);
            View txtIntro4_1 = page.findViewById(R.id.txtIntro4_1);
            View imgIntro4 = page.findViewById(R.id.imgIntro4);
            View imgIntro6 = page.findViewById(R.id.imgIntro6);
            View txtIntro6 = page.findViewById(R.id.txtIntro6);
            // The page is currently being scrolled / swiped. This is
            // a good place to show animations that react to the user's
            // swiping as it provides a good user experience.

            // Let's start by animating the Name.
            // We want it to fade as it scrolls out


            // We're attempting to create an effect for a View
            // specific to one of the pages in our ViewPager.
            // In other words, we need to check that we're on
            // the correct page and that the View in question
            // isn't null.
            if (pagePosition == 0) {

                txtIntro1_1.setTranslationX(-pageWidthTimesPosition);
                txtIntro1.setAlpha(1.0f - absPosition);
                imgIntro1.setTranslationX(pageWidthTimesPosition * 0.8f);

            }

            else if (pagePosition == 1) {
                txtIntro2_1.setTranslationX(pageWidthTimesPosition);
                imgIntro2.setTranslationX(-pageWidthTimesPosition);
                imgIntro2.setAlpha(1.0f - absPosition);
            }

            else if (pagePosition == 2)
            {
                txtIntro3_1.setTranslationX(pageWidthTimesPosition);
                imgIntro3.setTranslationX(-pageWidthTimesPosition);
                imgIntro3.setAlpha(1.0f - absPosition);
            }

            else if (pagePosition == 3)
            {
                txtIntro4_1.setTranslationX(pageWidthTimesPosition);
                imgIntro4.setTranslationX(-pageWidthTimesPosition);
                imgIntro4.setAlpha(1.0f - absPosition);
            }

            else
            {
                imgIntro6.setTranslationX(pageWidthTimesPosition * 0.8f);
                txtIntro6.setTranslationX(pageWidthTimesPosition * 0.8f);
            }


            // Finally, it can be useful to know the direction
            // of the user's swipe - if we're entering or exiting.
            // This is quite simple:
            if (position < 0) {
                // Create your out animation here
                if (pagePosition == 1)
                {
                    txtIntro2_1.setTranslationX(-pageWidthTimesPosition);
                    txtIntro2.setAlpha(1.0f - absPosition);
                    imgIntro2.setTranslationX(pageWidthTimesPosition * 0.8f);
                }

                if (pagePosition == 2)
                {
                    txtIntro3_1.setTranslationX(-pageWidthTimesPosition);
                    txtIntro3.setAlpha(1.0f - absPosition);
                    imgIntro3.setTranslationX(pageWidthTimesPosition * 0.8f);
                }

                if (pagePosition == 3)
                {
                    txtIntro4_1.setTranslationX(-pageWidthTimesPosition);
                    txtIntro4.setAlpha(1.0f - absPosition);
                    imgIntro4.setTranslationX(pageWidthTimesPosition * 0.8f);
                }

            }

            else {
                // Create your in animation here
            }
        }
    }
}

