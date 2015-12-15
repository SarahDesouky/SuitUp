class UsersController < ApplicationController

skip_before_action :verify_authenticity_token

def index
	@users = User.all
	render json: @users
end

def find
	 @user=User.where( :twitter_id => params[:twitter_id]).take
	 render json: @user
end

# def new
# end

def create
	@user = User.create(user_params)
	if @user.save
		render json: @user, status: :created
	else
		 render json: @user.errors, status: :unprocessable_entity
	end 
end

def update
	@user = User.where(:twitter_id =>params[:twitter_id]).take
	if @user.update(user_params)
		head :no_content
	else 
		render json: @user.errors, status: :unprocessable_entity
	end
end

def getFriends
	@user = User.where(:twitter_id => params[:twitter_id]).take
	@users = @user.friends
	render json: @users
end

def findFriend
	@user = User.where(:id => params[:id]).take
	render json: @user
end

def addFriend
@user = User.where(:twitter_id =>params[:twitter_id]).take
@friendship = Friendship.new(:user_id=> @user.id, :friend_id=> user_params[:id])
if @friendship.save
	render json: @user
else 
	render json: @user.errors, status: :unprocessable_entity
end
end

def removeFriend
@user = User.where(:twitter_id => params[:twitter_id]).take
@friendship = Friendship.where(:user_id=> @user.id, :friend_id=> params[:id])
if @friendship.delete
	render json: @user
else 
	render json: @users.errors, status: :unprocessable_entity
end

end

# def edit
# end

# def update
# end

# def destroy
# end

def user_params
params.require(:user).permit(:id, :fname, :lname, :twitter_id, :date_of_birth, :email, :avatar_url, :gender, :country)
end

end	