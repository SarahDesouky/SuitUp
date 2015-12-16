class MessagesController < ApplicationController

# def getAllThreads

# 	@user = User.where(:twitter_id=> params[:twitter_id])
# 	@thread = MessageThreads.where(:user1_id => @user.id)
# 	render json @threads
# 	# @threads = Messages.uniq.pluck(:thread_id).where(:owner_id => @user.id OR :user_id => @user.id).pluck
# 	# render json: @threads
# end

def getMessagesInThread
@messages = Message.where(:thread_id => params[:thread_id]).all
end


end
