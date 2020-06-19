package com.neotys.kobiton.sampleproject;

import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;



    @RunWith(Suite.class)

    @Suite.SuiteClasses({
            KobitonAppium.class,
    })

    public class EndUserExperienceTestSuite {
        public static void main(String[] args) throws Exception {
            JUnitCore.main(EndUserExperienceTestSuite.class.getName());
        }
    }

