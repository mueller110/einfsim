<Configuration Name="Peg Pushing Evolution"><General><UpdateXMLFile>false</UpdateXMLFile><SimTimeStep>0.1</SimTimeStep><ScreenScale>904.0</ScreenScale><ScreenRefreshTime>0.1</ScreenRefreshTime><Animate3D>true</Animate3D></General><Experiment><Objects><World Name="World"><Class>simma.worlds.RectangularArena</Class><Width>1.05</Width><Height>0.7</Height><Depth>0.3</Depth></World><Robot Name="Rescuer"><Class>simma.robots.CircleRobot</Class><BoundingRadius>0.03</BoundingRadius><MotionTrail>false</MotionTrail><ResetMode>None</ResetMode><Height>0.15</Height><Controller Name="Brain"><Class>simma.ann.NeuroModulatorController</Class><!-- <Class>simma.ann.NeuroFeedForwardController</Class>--><Structure>20-6-3</Structure><LearnRate>0.01</LearnRate><MaxDose>1.0</MaxDose><ResetMode>None</ResetMode><Areas>1</Areas><Modulator Name="ModA"><Color>RED</Color></Modulator><Modulator Name="ModB"><Color>BLUE</Color></Modulator><Reaction><Class>simma.ann.modulator.BiasChange</Class><Positive>true</Positive></Reaction><Reaction><Class>simma.ann.modulator.BiasChange</Class><Positive>false</Positive></Reaction><Evolution Enabled="true"><Neurons>true</Neurons><Links>true</Links><Weights>true</Weights><MutationRate>0.0050</MutationRate><CrossoverPoints>1</CrossoverPoints><OutputBias>false</OutputBias></Evolution></Controller><Actuator Name="Two-Wheel Drive"><Class>simma.kernel.equipment.TwoWheelDrive</Class><Mass>0.15</Mass><Gain>5.0</Gain><Offset>-0.5</Offset><MotorResistance>2.5</MotorResistance><AxisLength>0.03</AxisLength><WheelRadius>0.01</WheelRadius><Position><R>0.0</R><Phi>0.0</Phi></Position></Actuator><Sensor Name="LoadSensor"><Class>simma.equipment.sensors.LoadSensor</Class><Position><R>0.030</R><Phi>-40.0</Phi></Position><GroundClearance>0.001</GroundClearance><Mass>0.0020</Mass></Sensor><Sensor Name="DamageSensor"><Class>simma.equipment.sensors.DamageSensor</Class><Position><R>0.030</R><Phi>-40.0</Phi></Position><GroundClearance>0.001</GroundClearance><Mass>0.0020</Mass></Sensor><Sensor Name="InfraRedLeftBehind"><Class>simma.equipment.sensors.InfraRedSensor</Class><Position><R>0.025</R><Phi>-135.0</Phi></Position><GroundClearance>0.015</GroundClearance><Mass>0.0020</Mass></Sensor><Sensor Name="InfraRedLeft"><Class>simma.equipment.sensors.InfraRedSensor</Class><Position><R>0.025</R><Phi>-90.0</Phi></Position><GroundClearance>0.015</GroundClearance><Mass>0.0020</Mass></Sensor><Sensor Name="InfraRedHalfLeft"><Class>simma.equipment.sensors.InfraRedSensor</Class><Position><R>0.025</R><Phi>-45.0</Phi></Position><GroundClearance>0.015</GroundClearance><Mass>0.0020</Mass></Sensor><Sensor Name="InfraRedCenterLeft"><Class>simma.equipment.sensors.InfraRedSensor</Class><Position><R>0.025</R><Phi>-15.0</Phi></Position><GroundClearance>0.015</GroundClearance><Mass>0.0020</Mass></Sensor><Sensor Name="InfraRedCenterRight"><Class>simma.equipment.sensors.InfraRedSensor</Class><Position><R>0.025</R><Phi>15.0</Phi></Position><GroundClearance>0.015</GroundClearance><Mass>0.0020</Mass></Sensor><Sensor Name="InfraRedHalfRight"><Class>simma.equipment.sensors.InfraRedSensor</Class><Position><R>0.025</R><Phi>45.0</Phi></Position><GroundClearance>0.015</GroundClearance><Mass>0.0020</Mass></Sensor><Sensor Name="InfraRedRight"><Class>simma.equipment.sensors.InfraRedSensor</Class><Position><R>0.025</R><Phi>90.0</Phi></Position><GroundClearance>0.015</GroundClearance><Mass>0.0020</Mass></Sensor><Sensor Name="InfraRedRightBehind"><Class>simma.equipment.sensors.InfraRedSensor</Class><Position><R>0.025</R><Phi>135.0</Phi></Position><GroundClearance>0.015</GroundClearance><Mass>0.0020</Mass></Sensor><Sensor Name="InfraRedBehind"><Class>simma.equipment.sensors.InfraRedSensor</Class><Position><R>0.025</R><Phi>180.0</Phi></Position><GroundClearance>0.015</GroundClearance><Mass>0.0020</Mass></Sensor><Sensor Name="SmellLeftBehind"><Class>simma.equipment.sensors.SmellSensor</Class><Position><R>0.025</R><Phi>-135.0</Phi></Position><GroundClearance>0.015</GroundClearance><Mass>0.0020</Mass><Substance>Smell</Substance></Sensor><Sensor Name="SmellLeft"><Class>simma.equipment.sensors.SmellSensor</Class><Position><R>0.025</R><Phi>-90.0</Phi></Position><GroundClearance>0.015</GroundClearance><Mass>0.0020</Mass><Substance>Smell</Substance></Sensor><Sensor Name="SmellHalfLeft"><Class>simma.equipment.sensors.SmellSensor</Class><Position><R>0.025</R><Phi>-45.0</Phi></Position><GroundClearance>0.015</GroundClearance><Mass>0.0020</Mass><Substance>Smell</Substance></Sensor><Sensor Name="SmellCenterLeft"><Class>simma.equipment.sensors.SmellSensor</Class><Position><R>0.025</R><Phi>-15.0</Phi></Position><GroundClearance>0.015</GroundClearance><Mass>0.0020</Mass><Substance>Smell</Substance></Sensor><Sensor Name="SmellCenterRight"><Class>simma.equipment.sensors.SmellSensor</Class><Position><R>0.025</R><Phi>15.0</Phi></Position><GroundClearance>0.015</GroundClearance><Mass>0.0020</Mass><Substance>Smell</Substance></Sensor><Sensor Name="SmellHalfRight"><Class>simma.equipment.sensors.SmellSensor</Class><Position><R>0.025</R><Phi>45.0</Phi></Position><GroundClearance>0.015</GroundClearance><Mass>0.0020</Mass><Substance>Smell</Substance></Sensor><Sensor Name="SmellRight"><Class>simma.equipment.sensors.SmellSensor</Class><Position><R>0.025</R><Phi>90.0</Phi></Position><GroundClearance>0.015</GroundClearance><Mass>0.0020</Mass><Substance>Smell</Substance></Sensor><Sensor Name="SmellRedRightBehind"><Class>simma.equipment.sensors.SmellSensor</Class><Position><R>0.025</R><Phi>135.0</Phi></Position><GroundClearance>0.015</GroundClearance><Mass>0.0020</Mass><Substance>Smell</Substance></Sensor><Sensor Name="SmellBehind"><Class>simma.equipment.sensors.SmellSensor</Class><Position><R>0.025</R><Phi>180.0</Phi></Position><GroundClearance>0.015</GroundClearance><Mass>0.0020</Mass><Substance>Smell</Substance></Sensor><!--<Sensor Name="LightLeft"> <Class>simma.equipment.sensors.ParticleSensor</Class> 
					<Position> <R>0.015</R> <Phi>-90.0</Phi> </Position> <GroundClearance>0.06</GroundClearance> 
					<Mass>0.0020</Mass> <Substance>Photon</Substance> <ApertureAngle>90.0</ApertureAngle> 
					</Sensor> <Sensor Name="LightCenter"> <Class>simma.equipment.sensors.ParticleSensor</Class> 
					<Position> <R>0.0175</R> <Phi>0.0</Phi> </Position> <GroundClearance>0.045</GroundClearance> 
					<Mass>0.0020</Mass> <Substance>Photon</Substance> <ApertureAngle>90.0</ApertureAngle> 
					</Sensor> <Sensor Name="LightRight"> <Class>simma.equipment.sensors.ParticleSensor</Class> 
					<Position> <R>0.015</R> <Phi>90.0</Phi> </Position> <GroundClearance>0.06</GroundClearance> 
					<Mass>0.0020</Mass> <Substance>Photon</Substance> <ApertureAngle>90.0</ApertureAngle> 
					</Sensor> --><Mass>0.2</Mass></Robot><!--
			<Peg Name="LostPeg">
				<Class>simma.equipment.LostPeg</Class>
				<Particle Name="Heat">
					<Quantum>1.0</Quantum>
					<Color>BLUE</Color>
				</Particle>
				<BoundingRadius>0.02</BoundingRadius>
				<Height>0.015</Height>
				<Color>RED</Color>
				<ResetMode>Random</ResetMode>
				<MotionTrail>true</MotionTrail>
			</Peg>
			<Peg Name="LostPeg">
				<Class>simma.equipment.LostPeg</Class>
				<Particle Name="Heat">
					<Quantum>1.0</Quantum>
					<Color>BLUE</Color>
				</Particle>
				<BoundingRadius>0.02</BoundingRadius>
				<Height>0.015</Height>
				<Color>RED</Color>
				<ResetMode>Random</ResetMode>
				<MotionTrail>true</MotionTrail>
			</Peg>
			<Peg Name="LostPeg">
				<Class>simma.equipment.LostPeg</Class>
				<Particle Name="Heat">
					<Quantum>1.0</Quantum>
					<Color>BLUE</Color>
				</Particle>
				<BoundingRadius>0.02</BoundingRadius>
				<Height>0.015</Height>
				<Color>RED</Color>
				<ResetMode>Random</ResetMode>
				<MotionTrail>true</MotionTrail>
			</Peg>
--><!--<Peg Name="LostPeg"> <Class>simma.equipment.LostPeg</Class> 
				<Particle Name="Heat"> <Quantum>1.0</Quantum> <Color>WHITE</Color> </Particle> 
				<BoundingRadius>0.02</BoundingRadius> <Height>0.015</Height> <Color>RED</Color> 
				<ResetMode>Random</ResetMode> <MotionTrail>true</MotionTrail> </Peg> <Peg 
				Name="LostPeg"> <Class>simma.equipment.LostPeg</Class> <Particle Name="Heat"> 
				<Quantum>1.0</Quantum> <Color>WHITE</Color> </Particle> <BoundingRadius>0.02</BoundingRadius> 
				<Height>0.015</Height> <Color>RED</Color> <ResetMode>Random</ResetMode> <MotionTrail>true</MotionTrail> 
				</Peg> <Peg Name="LostPeg"> <Class>simma.equipment.LostPeg</Class> <Particle 
				Name="Heat"> <Quantum>1.0</Quantum> <Color>WHITE</Color> </Particle> <BoundingRadius>0.02</BoundingRadius> 
				<Height>0.015</Height> <Color>RED</Color> <ResetMode>Random</ResetMode> <MotionTrail>true</MotionTrail> 
				</Peg> <Peg Name="LostPeg"> <Class>simma.equipment.LostPeg</Class> <Particle 
				Name="Heat"> <Quantum>1.0</Quantum> <Color>WHITE</Color> </Particle> <BoundingRadius>0.02</BoundingRadius> 
				<Height>0.015</Height> <Color>RED</Color> <ResetMode>Random</ResetMode> <MotionTrail>true</MotionTrail> 
				</Peg> <Peg Name="LostPeg"> <Class>simma.equipment.LostPeg</Class> <Particle 
				Name="Heat"> <Quantum>1.0</Quantum> <Color>WHITE</Color> </Particle> <BoundingRadius>0.02</BoundingRadius> 
				<Height>0.015</Height> <Color>RED</Color> <ResetMode>Random</ResetMode> <MotionTrail>true</MotionTrail> 
				</Peg> <Peg Name="LostPeg"> <Class>simma.equipment.LostPeg</Class> <Particle 
				Name="Heat"> <Quantum>1.0</Quantum> <Color>WHITE</Color> </Particle> <BoundingRadius>0.02</BoundingRadius> 
				<Height>0.015</Height> <Color>RED</Color> <ResetMode>Random</ResetMode> <MotionTrail>true</MotionTrail> 
				</Peg> <Peg Name="LostPeg"> <Class>simma.equipment.LostPeg</Class> <Particle 
				Name="Heat"> <Quantum>1.0</Quantum> <Color>WHITE</Color> </Particle> <BoundingRadius>0.02</BoundingRadius> 
				<Height>0.015</Height> <Color>RED</Color> <ResetMode>Random</ResetMode> <MotionTrail>true</MotionTrail> 
				</Peg> --><Ghost Name="Ghost"><Class>simma.equipment.Ghost</Class><Particle Name="Heat"><Quantum>1.0</Quantum><Color>WHITE</Color></Particle><BoundingRadius>0.045</BoundingRadius><Height>0.015</Height><Color>WHITE</Color><ResetMode>Random</ResetMode><MotionTrail>false</MotionTrail><MoveArea><X>0.1</X><Y>0.1</Y><XX>0.98</XX><YY>0.67</YY></MoveArea><Speed><VX>0.01</VX><VY>0.01</VY></Speed></Ghost><!--<Ghost Name="Ghost"> <Class>simma.equipment.Ghost</Class> <Particle 
				Name="Smell"> <Quantum>1.0</Quantum> <Color>WHITE</Color> </Particle> <BoundingRadius>0.05</BoundingRadius> 
				<Height>0.015</Height> <Color>WHITE</Color> <ResetMode>Random</ResetMode> 
				<MotionTrail>false</MotionTrail> <MoveArea> <X>0.3</X> <Y>0.1</Y> <XX>0.98</XX> 
				<YY>0.67</YY> </MoveArea> <Speed> <VX>0.001</VX> <VY>0.001</VY> </Speed> 
				</Ghost> <Ghost Name="Ghost"> <Class>simma.equipment.Ghost</Class> <Particle 
				Name="Smell"> <Quantum>1.0</Quantum> <Color>WHITE</Color> </Particle> <BoundingRadius>0.05</BoundingRadius> 
				<Height>0.015</Height> <Color>WHITE</Color> <ResetMode>Random</ResetMode> 
				<MotionTrail>false</MotionTrail> <MoveArea> <X>0.01</X> <Y>0.01</Y> <XX>0.98</XX> 
				<YY>0.67</YY> </MoveArea> <Speed> <VX>0.00</VX> <VY>0.01</VY> </Speed> </Ghost> --><Spot Name="Spot"><Class>simma.equipment.Spot</Class><Particle Name="Smell"><Quantum>1.0</Quantum><Color>YELLOW</Color></Particle><BoundingRadius>0.06</BoundingRadius><Color>yellow</Color><ResetMode>None</ResetMode></Spot></Objects><Reporter Name="RescueTaskReporter"><Class>simma.reporters.RescueTaskReporter</Class><ReportObject>Rescuer</ReportObject><EpisodeCount>20</EpisodeCount><TestPhase>false</TestPhase><ReportTimeInterval>600.0</ReportTimeInterval></Reporter></Experiment><Evolution><Runs>1</Runs><PopulationSize>20</PopulationSize><MinimalGenerations>10</MinimalGenerations><MaximalGenerations>600</MaximalGenerations><FitnessThreshold>300000.0</FitnessThreshold></Evolution></Configuration>