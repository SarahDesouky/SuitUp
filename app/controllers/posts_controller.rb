class PostsController < ApplicationController

#returns a list of posts for a specific user
def getMyPosts
	@user = User.find(params[:twitter_id])
	@posts = @user.posts_on_own_wall.all
	render json: @posts
end

end
