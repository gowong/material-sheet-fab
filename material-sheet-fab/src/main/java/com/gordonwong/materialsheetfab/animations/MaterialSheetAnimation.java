package com.gordonwong.materialsheetfab.animations;

import java.lang.reflect.Method;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import io.codetail.animation.SupportAnimator;

/**
 * Created by Gordon Wong on 7/5/2015.
 *
 * Animates the material sheet into and out of view.
 */
public class MaterialSheetAnimation {

	private static final String SUPPORT_CARDVIEW_CLASSNAME = "android.support.v7.widget.CardView";
	private static final int SHEET_REVEAL_OFFSET_Y = 5;
	private boolean isRTL = false;

	private View sheet;
	private int sheetColor;
	private int fabColor;
	private Interpolator interpolator;
	private Method setCardBackgroundColor;
	private boolean isSupportCardView;

	public MaterialSheetAnimation(View sheet, int sheetColor, int fabColor,
			Interpolator interpolator, boolean isRTL) {
		this.isRTL = isRTL;
		this.sheet = sheet;
		this.sheetColor = sheetColor;
		this.fabColor = fabColor;
		this.interpolator = interpolator;
		isSupportCardView = sheet.getClass().getName().equals(SUPPORT_CARDVIEW_CLASSNAME);
		// Get setCardBackgroundColor() method
		if (isSupportCardView) {
			try {
				// noinspection unchecked
				setCardBackgroundColor = sheet.getClass()
						.getDeclaredMethod("setCardBackgroundColor", int.class);
			} catch (Exception e) {
				setCardBackgroundColor = null;
			}
		}
	}

	/**
	 * Aligns the sheet's position with the FAB.
	 * 
	 * @param fab Floating action button
	 */
	public void alignSheetWithFab(View fab) {
		// NOTE: View.getLocationOnScreen() returns the view's coordinates on the screen
		// whereas other view methods like getRight() and getY() return coordinates relative
		// to the view's parent. Using those methods can lead to incorrect calculations when
		// the two views do not have the same parent.

		// Get FAB's coordinates
		int[] fabCoords = new int[2];
		fab.getLocationOnScreen(fabCoords);
		int fabLeft = fabCoords[0];
		int fabRight = fabCoords[0] + fab.getWidth();
		int fabBottom = fabCoords[1] + fab.getHeight();

		// Get sheet's coordinates
		int[] sheetCoords = new int[2];
		sheet.getLocationOnScreen(sheetCoords);
		ViewGroup.MarginLayoutParams sheetLayoutParams = (ViewGroup.MarginLayoutParams) sheet
				.getLayoutParams();
		int sheetLeft = sheetCoords[0] - sheetLayoutParams.leftMargin;	
		int sheetRight = sheetCoords[0] + sheet.getWidth() + sheetLayoutParams.rightMargin;
		int sheetBottom = sheetCoords[1] + sheet.getHeight() + sheetLayoutParams.bottomMargin;

		int bottomDiff = (sheetBottom - fabBottom);
		int horizontalDiff = isRTL ? (sheetLeft - fabLeft) : (sheetRight - fabRight);

		// Set sheet's new coordinates (only if there is a change in coordinates because
		// setting the same coordinates can cause the view to "drift" - moving 0.5 to 1 pixels
		// around the screen)
		if (horizontalDiff != 0) {
			sheet.setX(sheet.getX() - horizontalDiff);
		}
		if (bottomDiff != 0) {
			sheet.setY(sheet.getY() - bottomDiff);
		}
	}

	/**
	 * Shows the sheet by morphing the FAB into the sheet.
	 *
	 * @param fab Floating action button
	 * @param showSheetDuration Duration of the sheet animation in milliseconds. Use 0 for no
	 *            animation.
	 * @param showSheetColorDuration Duration of the color animation in milliseconds. Use 0 for no
	 *            animation.
	 * @param listener Listener for animation events.
	 */
	public void morphFromFab(View fab, long showSheetDuration, long showSheetColorDuration,
			AnimationListener listener) {
		sheet.setVisibility(View.VISIBLE);
		revealSheetWithFab(fab, getFabRevealRadius(fab), getSheetRevealRadius(), showSheetDuration,
				fabColor, sheetColor, showSheetColorDuration, listener);
	}

	/**
	 * Hides the sheet by morphing the sheet into the FAB.
	 *
	 * @param fab Floating action button
	 * @param hideSheetDuration Duration of the sheet animation in milliseconds. Use 0 for no
	 *            animation.
	 * @param hideSheetColorDuration Duration of the color animation in milliseconds. Use 0 for no
	 *            animation.
	 * @param listener Listener for animation events.
	 */
	public void morphIntoFab(View fab, long hideSheetDuration, long hideSheetColorDuration,
			AnimationListener listener) {
		revealSheetWithFab(fab, getSheetRevealRadius(), getFabRevealRadius(fab), hideSheetDuration,
				sheetColor, fabColor, hideSheetColorDuration, listener);
	}

	protected void revealSheetWithFab(View fab, float startRadius, float endRadius,
			long sheetDuration, int startColor, int endColor, long sheetColorDuration,
			AnimationListener listener) {
		if (listener != null) {
			listener.onStart();
		}

		// Pass listener to the animation that will be the last to finish
		AnimationListener revealListener = (sheetDuration >= sheetColorDuration) ? listener : null;
		AnimationListener colorListener = (sheetColorDuration > sheetDuration) ? listener : null;

		// Start animations
		startCircularRevealAnim(sheet, getSheetRevealCenterX(), getSheetRevealCenterY(fab),
				startRadius, endRadius, sheetDuration, interpolator, revealListener);
		startColorAnim(sheet, startColor, endColor, sheetColorDuration, interpolator,
				colorListener);
	}

	protected void startCircularRevealAnim(View view, int centerX, int centerY, float startRadius,
			float endRadius, long duration, Interpolator interpolator,
			final AnimationListener listener) {
		// Use native circular reveal on Android 5.0+
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			// Setup animation
			// Native circular reveal uses coordinates relative to the view
			Animator anim = ViewAnimationUtils.createCircularReveal(view, centerX, centerY,
					startRadius, endRadius);
			anim.setDuration(duration);
			anim.setInterpolator(interpolator);
			// Add listener
			anim.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationStart(Animator animation) {
					if (listener != null) {
						listener.onStart();
					}
				}

				@Override
				public void onAnimationEnd(Animator animation) {
					if (listener != null) {
						listener.onEnd();
					}
				}
			});
			// Start animation
			anim.start();
		} else {
			// Circular reveal library uses absolute coordinates
			centerX += view.getX();
			centerY += view.getY();
			// Setup animation
			SupportAnimator anim = io.codetail.animation.ViewAnimationUtils
					.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
			anim.setDuration((int) duration);
			anim.setInterpolator(interpolator);
			// Add listener
			anim.addListener(new SupportAnimator.SimpleAnimatorListener() {
				@Override
				public void onAnimationStart() {
					if (listener != null) {
						listener.onStart();
					}
				}

				@Override
				public void onAnimationEnd() {
					if (listener != null) {
						listener.onEnd();
					}
				}
			});
			// Start animation
			anim.start();
		}
	}

	protected void startColorAnim(final View view, final int startColor, final int endColor,
			long duration, Interpolator interpolator, final AnimationListener listener) {
		// Setup animation
		ValueAnimator anim = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endColor);
		anim.setDuration(duration);
		anim.setInterpolator(interpolator);
		// Add listeners
		anim.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
				if (listener != null) {
					listener.onStart();
				}
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				if (listener != null) {
					listener.onEnd();
				}
			}
		});
		anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animator) {
				// Update background color
				Integer color = (Integer) animator.getAnimatedValue();

				// Use CardView.setCardBackgroundColor() to avoid crashes on Android < 5.0 and to
				// properly set the card's background color without removing the card's other styles
				// See https://github.com/gowong/material-sheet-fab/pull/2 and
				// https://code.google.com/p/android/issues/detail?id=77843
				if (isSupportCardView) {
					// Use setCardBackground() method if it is available
					if (setCardBackgroundColor != null) {
						try {
							setCardBackgroundColor.invoke(sheet, color);
						} catch (Exception e) {
							// Ignore exceptions since there's no other way set a support CardView's
							// background color
						}
					}
				}
				// Set background color for all other views
				else {
					view.setBackgroundColor(color);
				}
			}
		});
		// Start animation
		anim.start();
	}

	public void setSheetVisibility(int visibility) {
		sheet.setVisibility(visibility);
	}

	public boolean isSheetVisible() {
		return sheet.getVisibility() == View.VISIBLE;
	}

	/**
	 * @return Sheet's center X coordinate
	 */
	public float getSheetCenterX() {
		return sheet.getX() + getSheetRevealCenterX();
	}

	/**
	 * @return Translation Y value for the reveal sheet animation
	 */
	public int getRevealTranslationY() {
		return sheet.getHeight() / SHEET_REVEAL_OFFSET_Y;
	}

	protected int getSheetRevealCenterX() {
		return sheet.getWidth() / 2;
	}

	protected int getSheetRevealCenterY(View fab) {
		return ((sheet.getHeight() * (SHEET_REVEAL_OFFSET_Y - 1) / SHEET_REVEAL_OFFSET_Y)
				- (fab.getHeight() / 2));
	}

	protected float getSheetRevealRadius() {
		return Math.max(sheet.getWidth(), sheet.getHeight());
	}

	protected float getFabRevealRadius(View fab) {
		return Math.max(fab.getWidth(), fab.getHeight()) / 2;
	}

}
