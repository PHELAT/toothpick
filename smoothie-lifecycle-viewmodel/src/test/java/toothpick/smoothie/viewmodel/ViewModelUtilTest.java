/*
 * Copyright 2016 Stephane Nicolas
 * Copyright 2016 Daniel Molinero Reguerra
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package toothpick.smoothie.viewmodel;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import android.app.Application;
import android.content.res.Configuration;
import androidx.fragment.app.FragmentActivity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import toothpick.Scope;
import toothpick.Toothpick;

@RunWith(RobolectricTestRunner.class)
public class ViewModelUtilTest {
  @Test
  public void testCloseOnClear() {
    // GIVEN
    ActivityController<FragmentActivity> activityController =
        Robolectric.buildActivity(FragmentActivity.class).create();
    FragmentActivity activity = activityController.get();
    Application application = RuntimeEnvironment.application;
    Scope activityScope = Toothpick.openScopes(application, activity);

    // WHEN
    ViewModelUtil.closeOnClear(activity, activityScope);
    activityController.destroy();

    // THEN
    assertThat(Toothpick.isScopeOpen(activity), is(false));
  }

  @Test
  public void testCloseOnClear_shouldNotCloseScope_whenConfigurationChange() {
    // GIVEN
    ActivityController<FragmentActivity> activityController =
        Robolectric.buildActivity(FragmentActivity.class).create();
    FragmentActivity activity = activityController.get();
    Application application = RuntimeEnvironment.application;
    Scope activityScope = Toothpick.openScopes(application, activity);

    // WHEN
    ViewModelUtil.closeOnClear(activity, activityScope);
    activityController.configurationChange(new Configuration());

    // THEN
    assertThat(Toothpick.isScopeOpen(activity), is(true));
  }
}