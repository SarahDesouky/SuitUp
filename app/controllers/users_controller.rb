class UsersController < ApplicationController

skip_before_action :verify_authenticity_token
# def index
# 	@users = User.all
# 	render json: @users
# end

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

# def edit
# end

# def update
# end

# def destroy
# end

def user_params
params.require(:user).permit(:fname, :lname, :twitter_id, :date_of_birth, :email, :avatar_url, :gender, :country)
end

end	