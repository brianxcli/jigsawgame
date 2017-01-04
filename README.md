# jigsawgame
### Issues and lessons learned
<p>1. When adding the background, scaling the background picture is needed to adjust to different screen sizes. But calling setScaleX() and setScaleY() doesn't work if we use addChild(child, z-index, tag). Instead, we use addChild(child).</p>
<p>Reason: </p>
<p>2. Game crashes when unmute the sound.</p>
<p>Related code is in org.cocos2d.sound.SoundEngine. When the engine unmutes the sound, it just resume the original volumns saved in the variables, prevEffectsVolumn and prevSoundsVolumn, of type Float which are not initialized when the engine starts and when it mutes the sounds and effects. We should set a value, say 1.0, to both of the variables using setSoundVolumn() and setEffectsVolumn().</p>
<p>3. Before the sprites fade in, set their opacity to zero.</p>
<p>4. All callback methods of Cocos2d animations should be declared as public methods.</p>
<p>5. Add jar libraries to Android Studio projects:</p>
<p>File -> Project Structure -> Project Setting -> libraries</p>
<p>6. Initialize Gradle Wrapper:</p>
<p>Right click project -> Open Module Setting -> Add a module(Android Gradle)</p>
<p>7. Randomize number series:</p>
<p>Assume that there is an integer array of size n, what is the best way to randomize the first m elements (m is no larger than n)?</p>
<p>For each of the first m elements, switching with a random element.</p>
<p>8. Wrong fragments which obviously do not belong to the current pictures</p>
<p>Use an exclusive key for each picture, say, the picture file names.</p>
