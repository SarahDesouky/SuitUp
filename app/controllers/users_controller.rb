class UsersController < ApplicationController

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

# def create
# end

# def edit
# end

# def update
# end

# def destroy
# end

end