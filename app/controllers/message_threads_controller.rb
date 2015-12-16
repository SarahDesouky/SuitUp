class MessageThreadsController < ApplicationController

	skip_before_action :verify_authenticity_token


def getAllThreads

	@user = User.where(:twitter_id=> params[:twitter_id]).take
	# @projects = Project.where("manager_user_id = '#{current_user.id}' or account_manager_id = '#{current_user.id}'")

	@threads = MessageThread.where("user_id = '#{@user.id}' or receiver_id = '#{@user.id}'")
	render json: @threads
	# @threads = Messages.uniq.pluck(:thread_id).where(:owner_id => @user.id OR :user_id => @user.id).pluck
	# render json: @threads
end

def addThread
@thread = MessageThread.create(thread_params)
if @thread.save
		render json: @thread, status: :created
	else
		 render json: @thread.errors, status: :unprocessable_entity
	end 

end

def findThread
@user = User.where(:id=> params[:user_id]).take
@friend = User.where(:id => params[:receiver_id]).take

# @thread = MessageThread.where(:user_id => params[:user_id]  or :receiver_id => params[:user.id] 
# 	and (:user_id => params[:receiver_id] or :receiver_id => params[:receiver_id]))
@thread = MessageThread.where("user_id = '#{@user.id}' or receiver_id = '#{@user.id}' and user_id = '#{@friend.id}' or receiver_id = '#{@friend.id}'").take
render json: @thread
end

def getThread
@thread = MessageThread.where(:id => params['thread_id']).take
render json: @thread
end

def thread_params
params.require(:message_thread).permit(:user_id, :id, :receiver_id)
end


end
