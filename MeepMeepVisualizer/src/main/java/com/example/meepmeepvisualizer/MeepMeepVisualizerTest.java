package com.example.meepmeepvisualizer;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import org.rowlandhall.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import org.rowlandhall.meepmeep.core.entity.Entity;

public class MeepMeepVisualizerTest {
        public static void main(String[] args) {
            // Declare a MeepMeep instance
            // With a field size of 800 pixels
            MeepMeep meepMeep = new MeepMeep(800);

            RoadRunnerBotEntity RoboOne = new DefaultBotBuilder(meepMeep)
                    .setColorScheme(new ColorSchemeBlueDark())
                    // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                    .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 13.3)
                    .followTrajectorySequence(drive ->
                            drive.trajectorySequenceBuilder(new Pose2d(15, 70, 0))
                                    .lineToLinearHeading(new Pose2d(12, 36, Math.toRadians(270)))
                                    .waitSeconds(1.5)
                                    .lineToLinearHeading(new Pose2d(48,40, Math.toRadians(90)))
                                    .waitSeconds(1)
                                    .splineToConstantHeading(new Vector2d(62, 52), Math.toRadians(0))
                                    .waitSeconds(1)
                                    .lineToConstantHeading(new Vector2d(58, 44))
                                    .waitSeconds(0.5)
                                    .lineToConstantHeading(new Vector2d(62, 52))
                                    .waitSeconds(0.5)
                                    .lineToConstantHeading(new Vector2d(68, 44))
                                    .waitSeconds(0.5)
                                    .lineToConstantHeading(new Vector2d(62, 52))
                                    .waitSeconds(0.5)
                                    .build()
                    );
            RoadRunnerBotEntity RoboTwo = new DefaultBotBuilder(meepMeep)
                    .setColorScheme(new ColorSchemeRedDark())
                    // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                    .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 13.14)
                    .followTrajectorySequence(drive ->
                            drive.trajectorySequenceBuilder(new Pose2d(15, -70, 0))
                                    .lineToLinearHeading(new Pose2d(12, -36, Math.toRadians(270)))
                                    .waitSeconds(1.5)
                                    .lineToLinearHeading(new Pose2d(48,-12, Math.toRadians(90)))
                                    .waitSeconds(1)
                                    .strafeRight(10)
                                    .waitSeconds(1)
                                    .strafeRight(10)
                                    .waitSeconds(0.5)
                                    .strafeLeft(40)
                                    .waitSeconds(0.5)
                                    .build()
                    );

            RoadRunnerBotEntity RoboThree = new DefaultBotBuilder(meepMeep)
                    .setColorScheme(new ColorSchemeRedDark())
                    // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                    .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 13.3)
                    .followTrajectorySequence(drive ->
                            drive.trajectorySequenceBuilder(new Pose2d(-15, -70, 360))
                                    .lineToLinearHeading(new Pose2d(-12, -36, Math.toRadians(90)))
                                    .waitSeconds(1.5)
                                    .lineToLinearHeading(new Pose2d(-48,-40, Math.toRadians(270)))
                                    .waitSeconds(1)
                                    .splineToConstantHeading(new Vector2d(-62, -52), Math.toRadians(360))
                                    .waitSeconds(1)
                                    .lineToConstantHeading(new Vector2d(-58, -44))
                                    .waitSeconds(0.5)
                                    .lineToConstantHeading(new Vector2d(-62, -52))
                                    .waitSeconds(0.5)
                                    .lineToConstantHeading(new Vector2d(-68, -44))
                                    .waitSeconds(0.5)
                                    .lineToConstantHeading(new Vector2d(-62, -52))
                                    .waitSeconds(0.5)
                                    .build()
                    );
            RoadRunnerBotEntity RoboFour = new DefaultBotBuilder(meepMeep)
                    .setColorScheme(new ColorSchemeBlueDark())
                    // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                    .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 13.14)
                    .followTrajectorySequence(drive ->
                            drive.trajectorySequenceBuilder(new Pose2d(-15, 70, 360))
                                    .lineToLinearHeading(new Pose2d(-12, 36, Math.toRadians(90)))
                                    .waitSeconds(1.5)
                                    .lineToLinearHeading(new Pose2d(-48,12, Math.toRadians(270)))
                                    .waitSeconds(1)
                                    .strafeRight(10)
                                    .waitSeconds(1)
                                    .strafeRight(10)
                                    .waitSeconds(0.5)
                                    .strafeLeft(40)
                                    .waitSeconds(0.5)
                                    .build()
                    );
            // Set field image
            meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                    .setDarkMode(true)
                    // Background opacity from 0-1
                    .setBackgroundAlpha(0.95f)
                    .addEntity(RoboOne)
                    .addEntity(RoboTwo)
                    .addEntity(RoboThree)
                    .addEntity(RoboFour)
                    .start();
        }
}