class PostsController < ApplicationController

def getMyPosts
	@user = User.where(:twitter_id => params[:twitter_id]).take
	@posts = @user.posts_on_own_wall.all
	render json: @posts
end 

end
