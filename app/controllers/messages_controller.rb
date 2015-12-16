class MessagesController < ApplicationController

	skip_before_action :verify_authenticity_token


# def getAllThreads

# 	@user = User.where(:twitter_id=> params[:twitter_id])
# 	@thread = MessageThreads.where(:user1_id => @user.id)
# 	render json @threads
# 	# @threads = Messages.uniq.pluck(:thread_id).where(:owner_id => @user.id OR :user_id => @user.id).pluck
# 	# render json: @threads
# end

def getMessagesInThread
@messages = Message.where(:thread_id => params[:thread_id]).all
render json: @messages
end

def addMessage
@message = Message.create(message_params)
if @message.save
	render json: @message
else 
	render json: @message.errors, status: :unprocessable_entity
end
end

def message_params
params.require(:message).permit(:id, :owner_id, :user_id, :text, :thread_id)
end

def MarkAsRead
@thread = MessageThread.where(:id => message_params[:thread_id]).take
# @messages = Message.where(:thread_id => @thread.id, :user_id => message_params[:owner_id]).update_all(:read => true)
@messages = Message.where(:thread_id => @thread.id).update_all(:read => true)
head :no_content
end


end
