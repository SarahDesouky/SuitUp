class MessageThreadsController < ApplicationController

def getAllThreads

	@user = User.where(:twitter_id=> params[:twitter_id]).take
	@threads = MessageThread.where(:user_id => @user.id)
	render json: @threads
	# @threads = Messages.uniq.pluck(:thread_id).where(:owner_id => @user.id OR :user_id => @user.id).pluck
	# render json: @threads
end


end
