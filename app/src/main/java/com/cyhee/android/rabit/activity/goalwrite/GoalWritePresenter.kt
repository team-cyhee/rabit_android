package com.cyhee.android.rabit.activity.goalwrite

import android.net.Uri
import com.cyhee.android.rabit.client.PostClient
import com.cyhee.android.rabit.client.PutClient
import com.cyhee.android.rabit.model.GoalFactory
import com.cyhee.android.rabit.util.FileClient
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

class GoalWritePresenter(private val view: GoalWriteActivity) : GoalWriteContract.Presenter {

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(view) }

    override fun postGoal(goal: GoalFactory.Post) {
        PostClient.postGoal(goal, scopeProvider) {
            view.finish()
        }
    }

    override fun editGoal(id: Long, goal: GoalFactory.Post) {
        PutClient.putGoal(id, goal, scopeProvider) {
            view.finish()
        }
    }

    override fun postCompanion(id: Long, goal: GoalFactory.Post) {
        PostClient.postCompanion(id, goal, scopeProvider) {
            view.finish()
        }
    }

    override fun upload(goal: GoalFactory.Post, fileUri: Uri?) {
        if (fileUri != null)
            FileClient.uploadFile(fileUri, view, scopeProvider) { path ->
                goal.fileId = path.split("/").last().toLongOrNull()
                PostClient.postGoal(goal, scopeProvider) {
                    view.finish()
                }
            }
        else
            PostClient.postGoal(goal, scopeProvider) {
                view.finish()
            }
    }
}